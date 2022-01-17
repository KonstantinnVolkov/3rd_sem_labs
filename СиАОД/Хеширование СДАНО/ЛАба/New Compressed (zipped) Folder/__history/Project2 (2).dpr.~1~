program Project1;

{$APPTYPE CONSOLE}

uses
  SysUtils,
  windows;

const
  max = 500;

type
  TArr = array [0 .. max - 1] of pointer;
  TptMain = ^hMain;
  TptPage = ^hPage;
  coma = (none, add, search, edit, print, help, save, remove, quit);

  hMain = record
    title: string[30];
    parent: pointer;
    next, nextArr: TptMain;
    hDep: TptMain;
    hPages: TptPage;
  end;

  hPage = record
    page: string;
    next: TptPage;
  end;

  sA = ^sortAlph;

  sortAlph = record
    this: TptMain;
    next: sA;
  end;

function h(x: string; j: integer): integer;
var
  i, sum: integer;
begin
  sum := 0;
  for i := 1 to j do
    sum := sum + ord(UpCase(x[i]));

  result := sum mod max;
end;

procedure pageSort(var x: TptMain; hPag: TptPage);
var
  hThPag, hPrevPag: TptPage;
  ad: pointer;
begin
  if x^.hPages = nil then
    x^.hPages := hPag
  else
  begin
    if StrToInt(hPag^.page) < StrToInt(x^.hPages^.page) then
    begin
      hPag^.next := x^.hPages;
      x^.hPages := hPag;
    end
    else
    begin
      hThPag := x^.hPages;
      hPrevPag := hThPag;
      while (hThPag <> nil) do
      begin
        if (StrToInt(hPag^.page) > StrToInt(hThPag^.page)) then
        begin
          hPrevPag := hThPag;
        end;
        hThPag := hThPag^.next;
      end;
      ad := hPrevPag^.next;
      hPrevPag^.next := hPag;
      hPag^.next := ad;
    end;
  end;
end;

procedure wordSort(var x: sA; y: TptMain);
var
  hHead, hThis, hTemp, thisTemp: TptMain;
  tNew, tThis, tPrev: sA;
  temp: pointer;
begin
  new(tNew);
  tNew^.this := y;
  tNew^.next := nil;
  if x^.next <> nil then
  begin
    tThis := x^.next;
    hHead := y;
    if upperCase(hHead^.title) < upperCase(tThis^.this^.title) then
    begin
      x^.next := tNew;
      tNew^.next := tThis;
    end
    else
    begin
      tThis := x;
      tPrev := x;
      while (tThis^.next <> nil) and
        (upperCase(tThis^.next^.this^.title) < upperCase(tNew^.this^.title)) do
      begin
        tThis := tThis^.next;
      end;
      temp := tThis^.next;
      tThis^.next := tNew;
      tNew^.next := temp;
    end;
  end
  else
  begin
    x^.next := tNew;
  end;
end;

function readLine(): coma;
var
  line: string;
begin
  write('Enter comand: ');
  readln(line);
  if upperCase(line) = 'ADD' then
    result := add;
  if upperCase(line) = 'SEARCH' then
    result := search;
  if upperCase(line) = 'REMOVE' then
    result := remove;
  if upperCase(line) = 'EDIT' then
    result := edit;
  if upperCase(line) = 'PRINT' then
    result := print;
  if upperCase(line) = 'HELP' then
    result := help;
  if upperCase(line) = 'SAVE' then
    result := save;
  if upperCase(line) = 'QUIT' then
    result := quit;
end;

function checkVar(line: string; ma, mi: integer): bool;
var
  i: integer;
  res: boolean;
begin
  i := 1;
  res := true;
  while i <= length(line) do
  begin
    if not(line[i] in ['-', '0' .. '9']) then
    begin
      res := false;
      i := length(line);
    end;
    inc(i);
  end;
  if res then
    if (StrToInt(line) >= mi) and (StrToInt(line) <= ma) then
      res := true
    else if ma = -1 then
      res := true
    else
      res := false;
  result := res;
end;

function edtPages(): string;
var
  i: integer;
  line: string;
begin
  readln(line);
  i := 1;
  while i <= length(line) do
  begin
    if (line[i] = ' ') or not(line[i] in ['0' .. '9']) then
    begin
      if (line[i] = ',') and (line[i - 1] = ',') then
        delete(line, i, 1)
      else if line[i] <> ',' then
        delete(line, i, 1)
      else
        inc(i);
    end
    else
      inc(i);
  end;
  if (length(line) <> 0) then
  begin
    if (line[1] = ',') then
      delete(line, 1, 1);
    if (line[length(line)] = ',') then
      delete(line, length(line), 1);
  end;
  if length(line) <> 0 then
    result := line + ','
  else
    result := '';
end;

procedure writePages(var x: TptMain);
var
  pages, thisPage: string;
  i, j, k: integer;
  hPag: TptPage;
begin
  repeat
    write('Write pages divided by ",": ');
    pages := edtPages();
  until pages <> '';
  i := 1;
  j := 1;
  while j <= length(pages) do
  begin
    if pages[j] = ',' then
    begin
      k := j - i;
      thisPage := copy(pages, i, k);
      i := j + 1;
      new(hPag);
      hPag^.next := nil;
      hPag^.page := thisPage;
      if x^.hPages <> nil then
      begin
        pageSort(x, hPag);
      end
      else
      begin
        x^.hPages := hPag;
      end;
    end;
    inc(j);
  end;
end;

procedure addPages(var x: TptMain);
var
  pages, thisPage: string;
  i, j, k: integer;
  hPag, hThPag, hPrevPag: TptPage;
  ad: pointer;
begin
  writePages(x);

  hPag := x^.hPages;
  i := 0;
  while (hPag^.next <> nil) and (x^.hPages <> nil) do
  begin
    hPag := hPag^.next;
    inc(i);
  end;

  j := 1;
  hPag := x^.hPages;
  if i > 1 then
    while j <= i do
    begin
      thisPage := hPag^.page;
      hThPag := hPag^.next;
      hPrevPag := hPag;
      while (hThPag <> nil) and (j <= i) do
      begin
        if hThPag^.page = thisPage then
        begin
          hPrevPag^.next := hThPag^.next;
          dispose(hThPag);
          hThPag := hPrevPag^.next;
          inc(j);
        end
        else
          hThPag := hThPag^.next;
      end;
      inc(j);
      hPag := hPag^.next;
    end;
end;

procedure writeDep(var x: TptMain; var arr: TArr; check: boolean;
  dTCheck: string; var pt: sA);
var
  dTerm, line: string;
  dep, depThis, thDep: TptMain;
  k: integer;
  ptNext: sA;
begin
  if (upperCase(dTCheck) = 'YES') then
  begin
    repeat
      if (upperCase(dTCheck) = 'YES') or (check = false) then
      begin
        if check then
        begin
          write('Enter subterm: ');
          readln(dTerm);
          new(dep);
          dep^.title := dTerm;
          dep^.parent := x;
        end
        else
        begin
          write('Enter term: ');
          dep := x;
          dep^.parent := nil;
          readln(dTerm);
          dep^.title := dTerm;
          wordSort(pt, dep);
        end;

        dep^.nextArr := nil;
        dep^.next := nil;
        dep^.hPages := nil;
        dep^.hDep := nil;

        k := h(upperCase(dTerm), length(dTerm));

        if arr[k] = nil then
        begin
          arr[k] := dep;
        end
        else
        begin
          thDep := arr[k];
          while thDep^.nextArr <> nil do
          begin
            thDep := thDep^.nextArr;
          end;
          thDep^.nextArr := dep;
        end;

        depThis := x;
        if (depThis^.hDep = nil) and (check) then
          depThis^.hDep := dep
        else
        begin
          if check then
          begin
            depThis := x^.hDep;
            while depThis^.next <> nil do
              depThis := depThis^.next;
            depThis^.next := dep;
          end;
        end;

        addPages(dep);

        line := dep^.title;
        repeat
          if check then
            write('Add subterm to subterm ' + line + '? (Yes/No): ')
          else
            write('Add subterm to term ' + x^.title + '? (Yes/No): ');
          readln(dTCheck);

          if (upperCase(dTCheck) = 'NO') or (upperCase(dTCheck) = 'YES') then
            writeDep(dep, arr, true, dTCheck, pt);
        until upperCase(dTCheck) = 'NO';

      end;
    until upperCase(dTCheck) = 'NO';
  end;
end;

procedure hAdd(var arr: TArr; var pt: sA);
var
  x: TptMain;
begin
  writeln('--- ADD ---');
  new(x);
  writeDep(x, arr, false, 'yes', pt);
end;

procedure writeDepReq(temp, x: TptMain; line: string);
var
  page: TptPage;
begin
  while x <> nil do
  begin
    if x^.parent = temp then
    begin
      write(line + x^.title + ': ');
      page := x^.hPages;
      while page^.next <> nil do
      begin
        write(page^.page + ', ');
        page := page^.next;
      end;
      writeln(page^.page + ';');
      if x^.hDep <> nil then
        writeDepReq(x, x^.hDep, line + '  ');
      x := x^.next;
    end;
    if (x <> nil) and (x^.parent <> temp) then
      x := x^.next;
  end;

end;

procedure selectReq(var x: TptMain; k: integer; line: string);
var
  i: integer;
begin
  i := 0;

  while (x^.nextArr <> nil) and (i <> k) do
  begin
    if x^.title = line then
    begin
      inc(i);
    end;
    if i <> k then
      x := x^.nextArr;
  end;
end;

procedure writeReq(x: TptMain);
var
  page: TptPage;
begin
  write(x^.title + ': ');
  page := x^.hPages;
  while page^.next <> nil do
  begin
    write(page^.page + ', ');
    page := page^.next;
  end;
  writeln(page^.page + ';');
  if x^.hDep <> nil then
    writeDepReq(x, x^.hDep, '  ');

  writeln;
end;

procedure writeSearch(x: TptMain; line: string; out i: integer);
var
  xTemp: TptMain;
begin
  i := 0;
  while x <> nil do
  begin
    if upperCase(x^.title) = upperCase(line) then
    begin
      if x^.parent = nil then
        writeln(IntToStr(i + 1) + ') ' + x^.title)
      else
      begin
        xTemp := x;
        write(IntToStr(i + 1) + ') ');
        while xTemp^.parent <> nil do
        begin
          write(xTemp^.title + ' -> ');
          xTemp := xTemp^.parent;
        end;
        writeln(xTemp^.title);
      end;
      inc(i);
    end;
    x := x^.nextArr;
  end;
end;

procedure hSear(arr: TArr);
var
  sear: string;
  check: string[3];
  k, i: integer;
  x, xTemp: TptMain;
  page: TptPage;
begin
  writeln('--- SEARCH ---');
  write('Enter term/subterm to find: ');
  readln(sear);
  k := h(upperCase(sear), length(sear));
  if arr[k] = nil then
    writeln('We couldn''t find anything')
  else
  begin
    x := arr[k];
    writeSearch(x, sear, i);

    writeln(IntToStr(i) + 'matches were found');

    if i <> 0 then
      if i = 1 then
      begin
        repeat
          write('Show information? (Yes/No): ');
          readln(check);
        until (upperCase(check) = 'NO') or (upperCase(check) = 'YES');

        if upperCase(check) = 'YES' then
        begin
          writeln;
          if x^.parent = nil then
          begin
            writeReq(x);
          end
          else
          begin
            xTemp := x;
            while xTemp^.parent <> nil do
              xTemp := xTemp^.parent;
            writeReq(xTemp);
          end;
        end;
      end
      else
      begin
        repeat
          write('Enter way to show (0 - show all variants): ');
          readln(check);
        until checkVar(check, i, 0);
        writeln;
        if StrToInt(check) <> 0 then
        begin
          // Вывод 1 варианта
          x := arr[k];
          selectReq(x, StrToInt(check), sear);

          if x^.parent = nil then
          begin
            writeReq(x);
          end
          else
          begin
            while x^.parent <> nil do
              x := x^.parent;
            writeReq(x);
          end;
        end
        else
        begin
          // Вывод несколько вариантов
          x := arr[k];
          k := i;
          i := 0;

          while (i < k) do
          begin
            if x^.title = sear then
            begin
              if x^.parent = nil then
              begin
                writeReq(x);
              end
              else
              begin
                xTemp := x;
                while xTemp^.parent <> nil do
                  xTemp := xTemp^.parent;
                writeReq(xTemp);
              end;
              inc(i);
            end;
            x := x^.nextArr;
          end;

        end;

      end;
  end;
end;

procedure deleteDep(var x: TptMain; var arr: TArr; const Head: TptMain;
  var pt: sA);
var
  k: integer;
  xHead, xTemp, xTempDep, xTempNext: TptMain;
  this, sTemp: sA;
begin

  if x^.hDep <> nil then
  begin
    xTempDep := x^.hDep;
    deleteDep(xTempDep, arr, Head, pt);
  end;
  if x <> Head then
    if x^.next <> nil then
    begin
      xTempNext := x^.next;
      deleteDep(xTempNext, arr, Head, pt);
    end;

  k := h(x^.title, length(x^.title));
  if (arr[k] = x) then
  begin
    arr[k] := x^.nextArr;
    if x^.parent <> nil then
    begin
      xTemp := x^.parent;
      if xTemp^.hDep = x then
        xTemp^.hDep := x^.next
      else
      begin
        xTemp := xTemp^.hDep;
        while xTemp^.next <> x do
          xTemp := xTemp^.next;
        xTemp.next := x^.next;
      end;
    end;
  end
  else
  begin
    xTemp := arr[k];
    if arr[k] <> x then
    begin
      while (xTemp^.nextArr <> x) do
        xTemp := xTemp^.nextArr;
      xTemp^.nextArr := x^.nextArr;
      if x^.parent <> nil then
      begin
        xTemp := x^.parent;
        if xTemp^.hDep = x then
          xTemp^.hDep := x^.next
        else
        begin
          xTemp := xTemp^.hDep;
          while xTemp^.next <> x do
            xTemp := xTemp^.next;
          xTemp.next := x^.next;
        end;
      end;
    end;
  end;
  if x^.parent = nil then
  begin
    this := pt;
    sTemp := sTemp;
    while this^.next^.this <> x do
    begin
      this := this^.next;
    end;
    this^.next := this^.next^.next;
    dispose(x);
  end;
end;

procedure deleteTerm(var x: TptMain; var arr: TArr; var pt: sA);
var
  check: string[3];
  xTemp: TptMain;
begin
  writeln;
  writeln('Whole term :');
  writeReq(x);

  repeat
    writeln('Are you sure you want to delete whole term with subterms?(Yes/No) ');
    readln(check);
  until (upperCase(check) = 'NO') or (upperCase(check) = 'YES');

  if (upperCase(check) = 'YES') then
    deleteDep(x, arr, x, pt);
end;

procedure hRemove(var arr: TArr; var pt: sA);
var
  k, i: integer;
  check, line: string;
  x, xTemp: TptMain;

begin
  writeln('--- REMOVE ---');
  write('Enter term/subterm to delete: ');
  readln(line);
  k := h(line, length(line));
  if arr[k] = nil then
    writeln('Term "' + line + '" wasn''t found!')
  else
  begin
    x := arr[k];
    writeSearch(x, line, i);

    if i = 1 then
    begin
      // Удаление, когда 1 термин
      while (x <> nil) and (x^.title <> line) do
        if x^.title <> line then
          x := x^.next;

      deleteTerm(x, arr, pt);
    end
    else
    begin
      // Удаление, когда несколько терминов
      repeat
        write('Enter what to delete: ');
        readln(check);
      until checkVar(check, i, 1);

      x := arr[k];
      selectReq(x, StrToInt(check), line);

      deleteTerm(x, arr, pt);
    end;

  end;
end;

procedure editDep(var x: TptMain; var arr: TArr; var pt: sA); forward;

procedure editPages(var x: TptMain);
var
  page, pTemp: TptPage;
  checkInp: boolean;
  pageStr, check: string;
begin
  repeat
    page := x^.hPages;
    write('Current pages: ');
    while page^.next <> nil do
    begin
      write(page^.page + ', ');
      page := page^.next;
    end;
    writeln(page^.page + ';');
    repeat
      write('Enter page to edit: ');
      readln(pageStr);
    until checkVar(pageStr, -1, 1);

    page := x^.hPages;
    pTemp := page;
    while (page^.next <> nil) and (page^.page <> pageStr) do
    begin
      pTemp := page;
      page := page^.next;
    end;

    if (page^.next = nil) and (page^.page <> pageStr) then
    begin
      writeln('There is no such page!');
      checkInp := false;
    end
    else
    begin
      if x^.hPages^.page = pageStr then
        x^.hPages := page^.next
      else
        pTemp^.next := page^.next;

      repeat
        write('Old page: ' + pageStr +
          '. Enter new page ( enter 0 to delete page): ');
        readln(pageStr);
      until checkVar(pageStr, -1, -1);

      if StrToInt(pageStr) >= 0 then
      begin
        if StrToInt(pageStr) = 0 then
        begin
          x^.hPages := page;
          check := 'NO';
          writeln('You''ve got only one page left. Deleting isn''t available! ')
        end
        else
        begin
          dispose(page);
          new(page);
          page^.next := nil;
          page^.page := pageStr;
          pageSort(x, page);
        end;
      end;
      checkInp := true;
    end;

    repeat
      write('Continue page editing? (Yes/No) ');
      readln(check);
    until (upperCase(check) = 'NO') or (upperCase(check) = 'YES');

    if (upperCase(check) = 'NO') then
      checkInp := true
    else
      checkInp := false;
  until checkInp = true;

end;

procedure editWord(var x: TptMain; var arr: TArr; var pt: sA);
var
  k, kTemp: integer;
  line: string;
  xTemp: TptMain;
  tTemp, t: sA;
begin
  kTemp := h(x^.title, length(x^.title));
  write('"' + x^.title + '" old name. Enter new: ');
  readln(line);
  k := h(line, length(line));
  if arr[k] = nil then
    arr[k] := x
  else
  begin
    xTemp := arr[kTemp];
    if x = xTemp then
      arr[kTemp] := x^.nextArr
    else
    begin
      while xTemp^.nextArr <> x do
        xTemp := xTemp^.nextArr;
      xTemp^.nextArr := x^.nextArr;
    end;
    xTemp := arr[k];
    while xTemp^.nextArr <> nil do
      xTemp := xTemp^.nextArr;
    xTemp^.nextArr := x;
  end;
  if x^.parent = nil then
  begin
    tTemp := t;
    t := pt^.next;
    while t^.this^.title <> x^.title do
    begin
      tTemp := t;
      t := t^.next;
    end;
    t^.next := t^.next^.next;
    wordSort(pt, x);
  end;

  x^.title := line;
end;

procedure editVar(var x: TptMain; var arr: TArr; var pt: sA);
var
  p, c: string;
  kTemp, k, i: integer;
  xTemp: TptMain;
  page, pTemp: TptPage;
  line: string;
  checkInp: boolean;
  t, tTemp: sA;
  check: boolean;
begin
  writeln;
  writeln('Term: ');
  writeReq(x);
  writeln('What to edit?');
  writeln('1) Term name');
  writeln('2) Page list');
  if x^.hDep <> nil then
    writeln('3) Subterm list');
  writeln('4) Add subterm');
  writeln('5) Add page');
  writeln('6) Delete all');
  writeln('7) Quit');

  repeat
    write('Enter way to delete ');
    readln(c);
  until checkVar(c, 8, 1);

  case StrToInt(c) of
    1:
      editWord(x, arr, pt);
    2:
      editPages(x);
    3:
      editDep(x, arr, pt);
    4:
      writeDep(x, arr, true, 'yes', pt);
    5:
      writePages(x);
    6:
      deleteTerm(x, arr, pt);
  end;
end;

procedure editDep(var x: TptMain; var arr: TArr; var pt: sA);
var
  xTemp: TptMain;
  i: integer;
  pageStr: string;
begin
  x := x^.hDep;
  xTemp := x;
  i := 0;
  writeln;
  writeln('Available terms: ');
  while xTemp <> nil do
  begin
    inc(i);
    writeln(IntToStr(i) + ') ' + xTemp^.title);
    xTemp := xTemp^.next;
  end;

  repeat
    write('Choose term ');
    readln(pageStr);
  until checkVar(pageStr, i, 1);

  i := 1;
  while i <> StrToInt(pageStr) do
  begin
    inc(i);
    x := x^.next;
  end;

  editVar(x, arr, pt);
end;

procedure hEdit(var arr: TArr; var pt: sA);
var
  x, xTemp: TptMain;
  page: TptPage;
  line: string;
  check, p: string[3];
  k, kTemp, i, c: integer;
  checkInp: boolean;
begin
  writeln('--- TERM EDITING ---');
  write('Enter term/subterm to edit: ');

  readln(line);
  k := h(line, length(line));
  if arr[k] = nil then
    writeln('Term "' + line + '" wasn''t found')
  else
  begin
    x := arr[k];
    writeSearch(x, line, i);

    writeln(IntToStr(i) + ' matches were found');

    if i <> 0 then
      if i = 1 then
      begin
        selectReq(x, 1, line);
        editVar(x, arr, pt);
      end
      else
      begin
        // Редактирование 1 из нескольких
        repeat
          write('Enter term to edit ');
          readln(check);
        until checkVar(check, i, 1);

        x := arr[k];
        selectReq(x, StrToInt(check), line);
        editVar(x, arr, pt);
      end;
  end;

end;

procedure printDepPages(x: TptMain; sep: string);
type
  link = ^notAvail;

  notAvail = record
    item: TptMain;
    next: link;
  end;
var
  xHead: TptMain;
  temp, tempPrev, this, item, sHead: sA;
  page: TptPage;
begin
  if x <> nil then
  begin
    new(this);
    sHead := this;
    sHead^.next := nil;
    sHead^.this := nil;
    item := sHead;

    xHead := x;
    while x <> nil do
    begin
      new(this);
      this^.this := x;
      this^.next := nil;
      item^.next := this;
      item := item^.next;
      x := x^.next;
    end;
    while sHead^.next <> nil do
    begin
      this := sHead^.next;
      temp := sHead^.next;
      tempPrev := sHead;
      while this^.next <> nil do
      begin
        if StrToInt(temp^.this^.hPages^.page) >
          StrToInt(this^.next^.this^.hPages^.page) then
        begin
          tempPrev := this;
          temp := this^.next;
        end;
        this := this^.next;
      end;
      tempPrev^.next := temp^.next;
      write(sep + temp^.this^.title + ': ');
      page := temp^.this^.hPages;
      while page^.next <> nil do
      begin
        write(page^.page + ', ');
        page := page^.next;
      end;
      writeln(page^.page + ';');
      printDepPages(temp^.this^.hDep, sep + '  ');
      dispose(temp);
    end;
  end;
end;

procedure printPages(pt: sA);
var
  this, x: TptMain;
  i, j: integer;
  page: TptPage;
  ptThis, ptTemp, ptTempPrev, Head: sA;
begin
  if pt^.next = nil then
    writeln('No terms found!')
  else
  begin
    new(ptThis);
    Head := ptThis;
    Head^.this := nil;
    Head^.next := nil;
    ptTemp := Head;
    pt := pt^.next;
    writeln;
    while pt <> nil do
    begin
      new(ptThis);
      ptThis^.this := pt^.this;
      ptThis^.next := nil;
      ptTemp^.next := ptThis;
      ptTemp := ptTemp^.next;
      pt := pt^.next;
    end;

    while Head^.next <> nil do
    begin
      ptThis := Head^.next;
      ptTemp := Head^.next;
      ptTempPrev := Head;
      while ptThis^.next <> nil do
      begin
        if StrToInt(ptTemp^.this^.hPages^.page) >
          StrToInt(ptThis^.next^.this^.hPages^.page) then
        begin
          ptTempPrev := ptThis;
          ptTemp := ptThis^.next;
        end;
        ptThis := ptThis^.next;
      end;
      ptTempPrev^.next := ptTemp^.next;
      write(ptTemp^.this^.title + ': ');
      page := ptTemp^.this^.hPages;
      while page^.next <> nil do
      begin
        write(page^.page + ', ');
        page := page^.next;
      end;
      writeln(page^.page + ';');
      printDepPages(ptTemp^.this^.hDep, '  ');
      dispose(ptTemp);
    end;
    writeln;
  end;
end;

procedure writeAlph(x: TptMain; sep: string);
type
  link = ^notAvail;

  notAvail = record
    item: TptMain;
    next: link;
  end;
var
  this, pt, ptTemp: TptMain;
  i, j: integer;
  page: TptPage;
  temp, tempPrev, sH, sT: link;
begin
  if x <> nil then
  begin
    if x^.parent <> nil then
    begin
      this := x;
      new(sH);
      sH^.next := nil;
      sH^.item := nil;
      temp := sH;
      while this <> nil do
      begin
        new(sT);
        sT^.item := this;
        sT^.next := nil;
        temp^.next := sT;
        temp := temp^.next;
        this := this^.next;
      end;
      repeat
        temp := sH^.next;
        sT := temp;
        tempPrev := sH;
        while sT^.next <> nil do
        begin
          if temp^.item^.title > sT^.next^.item^.title then
          begin
            tempPrev := sT;
            temp := sT^.next;
          end;
          sT := sT^.next;
        end;
        tempPrev^.next := temp^.next;
        write(sep + temp^.item^.title + ': ');
        page := temp^.item^.hPages;
        while page^.next <> nil do
        begin
          write(page^.page + ', ');
          page := page^.next;
        end;
        writeln(page^.page + ';');
        writeAlph(temp^.item^.hDep, sep + '  ');
        dispose(temp);
      until sH^.next = nil;
    end;
    if x^.parent = nil then
    begin
      write(x^.title + ': ');
      page := x^.hPages;
      while page^.next <> nil do
      begin
        write(page^.page + ', ');
        page := page^.next;
      end;
      writeln(page^.page + ';');
      writeAlph(x^.hDep, '  ');
    end;
  end;
end;

procedure searchAlph(pt: sA);
var
  ptThis: sA;
begin
  ptThis := pt^.next;
  while ptThis <> nil do
  begin
    writeAlph(ptThis^.this, '');
    ptThis := ptThis^.next;
  end;
end;

procedure printAlph(pt: sA);
begin
  if pt^.next = nil then
    writeln('No terms found!')
  else
  begin
    writeln;
    searchAlph(pt);
    writeln;
  end;
end;

procedure hPrint(var pt: sA);
var
  checkDig: string;
begin
  writeln('--- PRINT ---');
  writeln('Data representation: ');
  writeln('1) Sort by alphabet;');
  writeln('2) Sort by pages');
  repeat
    write('Choose input method ');
    readln(checkDig);
  until checkVar(checkDig, 2, 1);
  case StrToInt(checkDig) of
    1:
      printAlph(pt);
    2:
      printPages(pt);
  end;

end;

procedure hHelp();
begin
  writeln('--- AVAILABLE COMMANDS ---');
  writeln;
  writeln('add - add term');
  writeln('search - find term/subterm');
  writeln('remove - delete term/subterm');
  writeln('edit - edit term/subterm');
  writeln('print - print whole base');
  writeln('help - print help');
  // writeln('save - сохранить изменения');
  // writeln('quit - выйти');
  writeln;
end;

procedure rFile(var pt: sA; var arr: TArr);
var
  this, Head: sA;
  f: textFile;
  x, dep: TptMain;
  line: string;
  i, j: integer;

  procedure readPage(x: TptMain; i, j: integer);
  var
    page, pThis: TptPage;
  begin
    new(page);
    pThis := page;
    x^.hPages := page;
    while i <= length(line) do
    begin
      while (line[i] <> ',') and (i <> length(line)) do
        inc(i);
      if i = length(line) then
      begin
        pThis^.page := copy(line, j, i - j + 1);
        inc(i);
        j := i;
        pThis^.next := nil;
      end
      else
      begin
        pThis^.page := copy(line, j, i - j);
        inc(i);
        j := i;
        new(page);
        pThis^.next := page;
        pThis := page;
      end;
    end;

  end;

  procedure readDep(xDep: TptMain; var par: string; sep: string);
  var
    x, y, temp: TptMain;
    k: integer;
  begin
    if par <> '$--' then
    begin
      i := 1;
      while par[i] = '-' do
        inc(i);
      if length(sep) = i - 1 then
      begin
        i := length(sep) + 1;
        while line[i] <> '$' do
          inc(i);
        if xDep^.hDep <> nil then
        begin
          y := xDep^.hDep;
          while y^.next <> nil do
            y := y^.next;
          new(x);
          y^.next := x;
        end
        else
        begin
          new(x);
          xDep^.hDep := x;
        end;
        x^.title := copy(par, length(sep) + 1, i - length(sep) - 1);
        x^.hDep := nil;
        x^.hPages := nil;
        x^.next := nil;
        x^.parent := xDep;

        k := h(x^.title, length(x^.title));
        if arr[k] = nil then
          arr[k] := x
        else
        begin
          temp := arr[k];
          while temp^.nextArr <> nil do
            temp := temp^.nextArr;
          temp^.nextArr := x;
          x^.nextArr := nil;
        end;

        inc(i);
        j := i;
        readPage(x, i, j);
        readln(f, par);
        readDep(xDep, par, sep);
      end
      else
      begin
        if length(sep) <= i - 1 then
        begin
          temp := xDep^.hDep;
          while temp^.next <> nil do
            temp := temp^.next;
          readDep(temp, par, sep + '-');
        end;
      end;
      if length(sep) <= i - 1 then
        readDep(xDep, par, sep);
    end;
  end;

  procedure readTerm(x: TptMain);
  var
    k: integer;
  begin
    readln(f, line);
    i := 1;
    while line[i] <> '$' do
      inc(i);
    x^.title := copy(line, 1, i - 1);
    x^.hDep := nil;
    x^.hPages := nil;
    x^.parent := nil;

    k := h(x^.title, length(x^.title));
    if arr[k] = nil then
      arr[k] := x
    else
    begin
      dep := arr[k];
      while dep^.nextArr <> nil do
        dep := dep^.nextArr;
      dep^.nextArr := x;
      x^.nextArr := nil;
    end;

    inc(i);
    j := i;

    readPage(x, i, j);
    readln(f, line);
    if line <> '$--' then
    begin
      readDep(x, line, '-');
    end;
  end;

begin
  AssignFile(f, 'lib.txt');
  reset(f);

  while (not EOF(f)) do
  begin
    new(x);
    readTerm(x);
    new(this);
    wordSort(pt, x);
  end;
  closeFile(f);

end;

var
  hArr: TArr;
  str: coma;
  pt: sA;

begin
  SetConsoleCP(1251);
  SetConsoleOutputCP(1251);
  writeln('List of comands: ');
  writeln('   Add');
  writeln('   Search');
  writeln('   Remove');
  writeln('   Edit');
  writeln('   Print');
  writeln('   Help');
  writeln('   Save');
  writeln('   Quit');
  new(pt);
  pt^.this := nil;
  pt^.next := nil;
  rFile(pt, hArr);
  repeat
    str := none;
    while str = none do
      str := readLine();
    case str of
      add:
        hAdd(hArr, pt);
      remove:
        hRemove(hArr, pt);
      search:
        hSear(hArr);
      edit:
        hEdit(hArr, pt);
      print:
        hPrint(pt);
      help:
        hHelp();
      // save: hSave(pt);
    end;
    writeln('---------------');
  until str = quit;

end.
