program laba1_var2;

{$APPTYPE CONSOLE}

{$R *.res}

uses
  System.SysUtils;

Type pt = ^elem;
 Elem = record
   number: integer;
   next : pt;
 end;

 var i,j,k: integer;
   n: integer;
   first,curr,temp: pt;
begin
  writeln('Введите k');
  readln(k);
  writeln('Каждый ',k,' ,будет удаляться: ');
  writeln(' ');
   writeln('|  Количество людей   |    Номер выжившего  |');

  for n := 1 to 64 do
 begin

  first:= nil;
  curr:=nil;
  for i := 1 to n do
  begin
  if first=nil then
    begin
      new(first);
      new(curr);
      first^.number :=i;
      first^.next:=nil;
      curr:=first;
    end
    else
      begin
      new(temp);
      temp^.number:= i;
      temp^.next:=nil;
      curr^.next:=temp;
      curr:=curr^.next;
    end;
  end;

  curr^.next:= first;
  curr:= first;
 // writeln('\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\');
   // writeln('                  Количесво человек - ', n);
   for i := 1 to n-1 do

    for j := 1 to k do
    begin
      temp:=curr;
       curr:=curr^.next;
       if j = k-1 then
       begin
          temp^.next:=curr^.next;
          dispose(curr);
          writeln('Уходит - ', curr^.number);
       end;
    end;



    writeln('            ', n, '                     ', curr^.number);
    writeln('------------------------|-----------------------');

   // writeln('                 Остается - ',curr^.number);
        // writeln('\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\');
 end;




  readln;
end.
