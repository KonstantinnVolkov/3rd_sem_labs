using JilbaAnalyther.Model;
using Microsoft.Win32;
using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.IO;
using System.Runtime.CompilerServices;
using System.Windows;
using System.Windows.Input;

namespace JilbaAnalyther.ViewModel
{
    class JilbaAnalyzerViewModel : INotifyPropertyChanged
    {
        private ICommand _loadCommand;

        private JilbaAnalyzerModel _CA = new JilbaAnalyzerModel();

        private string _text;
        private string _path;
        private string _maxNesting;
        private string _conditionalCount;
        private string _relativeDifficulity;

        public ICommand LoadCommand => _loadCommand ?? (_loadCommand = new DelegateCommand(Load));

        public string Text
        {
            get => _text;
            set
            {
                _text = value;
                NotifyPropertyChanged(nameof(Text));
            }
        }

        public string Path
        {
            get => _path;
            set
            {
                _path = value;
                NotifyPropertyChanged(nameof(Path));
            }
        }

        public string MaxNesting
        {
            get => _maxNesting;
            set
            {
                _maxNesting = value;
                NotifyPropertyChanged(nameof(MaxNesting));
            }
        }

        public string ConditionalCount
        {
            get => _conditionalCount;
            set
            {
                _conditionalCount = value;
                NotifyPropertyChanged(nameof(ConditionalCount));
            }
        }

        public string RelativeDifficulity
        {
            get => _relativeDifficulity;
            set
            {
                _relativeDifficulity = value;
                NotifyPropertyChanged(nameof(RelativeDifficulity));
            }
        }


        #region INotifyPropertyChanged
        public event PropertyChangedEventHandler PropertyChanged;

        private void NotifyPropertyChanged([CallerMemberName] string prop = "")
        {
            PropertyChanged?.Invoke(this, new PropertyChangedEventArgs(prop));
        }
        #endregion


        public void Load(object o = null)
        {
            OpenFileDialog ofd = new OpenFileDialog
            {
                Filter = "Текстовый файл|*.txt; *.csv"
            };

            if (ofd.ShowDialog() == true)
            {
                try
                {
                    Text = File.ReadAllText(ofd.FileName);
                    Path = ofd.FileName;
                    List<string> list = _CA.GetWordsList(Text);
                    ConditionalCount = _CA.GetConditionalsCount(list).ToString();
                    RelativeDifficulity = ((float)_CA.GetConditionalsCount(list) / _CA.GetOperatorsCount(list)).ToString("0.00");
                    _CA.ConvertElseIf(ref list);
                    MaxNesting = _CA.GetMaxNesting(list).ToString();
                }
                catch (Exception ex)
                {
                    MessageBox.Show(ex.Message);
                }
            }
        }
    }
}
