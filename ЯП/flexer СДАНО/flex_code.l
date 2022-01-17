%option noyywrap

%{
  int lines = 0;
  int adminLines = 0;
  float adminProcents = 0;
%}

%%

admin: {
        ++lines;
        ++adminLines;
        printf("%s", yytext);
       }
[_a-zA-z0-9]*: {
                ++lines;
                printf("%s", yytext);
              }

%%

int main()
{
  yylex();
  adminProcents = ((float)adminLines / lines) * 100;
  printf("\n\n\nThere's %d admins messages, which are %5.2f percentes", adminLines, adminProcents);
}
