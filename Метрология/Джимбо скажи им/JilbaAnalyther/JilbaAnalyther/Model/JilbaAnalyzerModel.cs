using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Text.RegularExpressions;
using System.Windows.Input;

namespace JilbaAnalyther.Model
{
    public delegate void ExecuteDelegate(object param);
    public delegate bool CanExecuteDelegate(object param);


    public class DelegateCommand : ICommand
    {
        private readonly ExecuteDelegate _execDelegate;
        private readonly CanExecuteDelegate _canExecuteDelegate;


        public event EventHandler CanExecuteChanged
        {
            add { }
            remove { }
        }

        public DelegateCommand(ExecuteDelegate executeDelegate)
        {
            _execDelegate = executeDelegate;
            _canExecuteDelegate = null;
        }

        public DelegateCommand(ExecuteDelegate executeDelegate, CanExecuteDelegate canExecuteDelegate)
        {
            _execDelegate = executeDelegate;
            _canExecuteDelegate = canExecuteDelegate;
        }


        public void Execute(object parameter) => _execDelegate(parameter);

        public bool CanExecute(object parameter) =>
            _canExecuteDelegate?.Invoke(parameter) ?? true;
    }


    public static class Extensions
    {
        public static bool In<T>(this T item, params T[] items) => items == null
            ? throw new ArgumentNullException("items")
            : items.Contains(item);

        public static void AddOrIncreaseValue<T>(this T map, string key) where T : Dictionary<string, int>
        {
            if (!map.ContainsKey(key))
                map.Add(key, 1);
            else
                map[key]++;
        }
    }


    public class JilbaAnalyzerModel
    {
        public struct State
        {
            public int nesting;
            public int endsCount;
            public bool isSwitch;

            public State(int nesting, int endsCount, bool isSwitch = false)
            {
                this.nesting = nesting;
                this.endsCount = endsCount;
                this.isSwitch = isSwitch;
            }
        }

        private readonly static string[] operators = { "as", "assert", "break", "case", "catch","continue",
            "do", "else", "extends", "finally", "for", "goto", "if", "implements", "import", "in",
            "instanceof", "new", "pull", "return", "switch", "throw", "throws", "try", "while",
            ";", ".", "=", "+", "-", "*", "/", "!", "%", "<", ">", ",", ":" };

        private readonly static string[] conditionalOperators = { "if", "for", "while", "case"};


        public void InsertSpaces(ref string text)
        {
            StringBuilder sb = new StringBuilder(text);

            for (int i = 0; i < sb.Length - 1; i++)
            {
                if (IsLetterOrSpaceOrNum(sb[i]) && !IsLetterOrSpaceOrNum(sb[i + 1]))
                    sb = sb.Insert(++i, ' ');

                else if (!IsLetterOrSpaceOrNum(sb[i]) && IsLetterOrSpaceOrNum(sb[i + 1]))
                    sb = sb.Insert(++i, ' ');

                else if (!IsLetterOrSpaceOrNum(sb[i]) && !IsLetterOrSpaceOrNum(sb[i + 1]))
                    sb = sb.Insert(++i, ' ');
            }

            text = sb.ToString();
        }

        public static void DelInvalid(ref string text)
        {
            StringBuilder sb = new StringBuilder(text);

            sb.Replace("\n", "");
            sb.Replace("\r", "");
            sb.Replace("\t", "");
            sb.Replace("\a", "");
            sb.Replace("\b", "");
            sb.Replace("\f", "");
            sb.Replace("\v", "");

            text = sb.ToString();

            Regex.Replace(text, "[ ]+", " ");
        }

        public List<string> GetWordsList(string text)
        {
            InsertSpaces(ref text);
            DelInvalid(ref text);

            return text.Split(new char[] { ' ', '(', ')', '[', ']' },
                StringSplitOptions.RemoveEmptyEntries).ToList();
        }

        public void ConvertElseIf(ref List<string> list)
        {
            for (var i = 0; i + 2 < list.Count; i++)
            {
                if (list[i] == "}" && list[i + 1] == "else" && list[i + 2] == "if")
                {
                    list.RemoveAt(i);
                    list.RemoveAt(i);
                    list[i] = "elseif";
                }
            }
        }

        public int GetConditionalsCount(List<string> list)
        {
            int count = 0;

            foreach (var item in list)
            {
                if (item.In(conditionalOperators))
                    count++;
            }

            return count;
        }

        public int GetOperatorsCount(List<string> list)
        {
            int count = 0;

            foreach (var item in list)
            {
                if (item.In(operators))
                    count++;
            }

            return count;
        }

        public int GetMaxNesting(List<string> list)
        {
            int nesting = 0, maxNesting = 0, endsCount = 0;
            Stack<State> stack = new Stack<State>();

            foreach (var item in list)
            {
                if (item.Equals("case") || item.Equals("elseif"))
                {
                    stack.Push(new State(nesting, endsCount, true));

                    if (++nesting > maxNesting)
                        maxNesting = nesting;

                    endsCount = 1;
                }
                else if (item.In(conditionalOperators))
                {
                    stack.Push(new State(nesting, endsCount));

                    if (++nesting > maxNesting)
                        maxNesting = nesting;

                    endsCount = 1;
                }
                else if (item.Equals("}"))
                {
                    if ((stack.Count != 0) && (endsCount != 0))
                    {
                        State state;

                        do
                        {
                            state = stack.Pop();

                            nesting = state.nesting;
                            endsCount = state.endsCount;
                        }
                        while (state.isSwitch && stack.Count != 0);
                    }
                }
            }

            return maxNesting == 0 ? maxNesting : maxNesting - 1;
        }

        public bool IsLetterOrSpaceOrNum(char c) =>
            c == 32 ||
            ((c >= 65) && (c <= 90)) ||
            ((c >= 97) && (c <= 122)) ||
            ((c >= 48) && (c <= 57));
    }
}
