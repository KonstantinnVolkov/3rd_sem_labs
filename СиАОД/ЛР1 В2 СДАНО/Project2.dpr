program Project2;

{$APPTYPE CONSOLE}
{$R *.res}

uses
  System.SysUtils;

type
  ptr = ^TElement;

  TElement = record
    next, prev: ptr;
    numberOfPlayer: integer;
    droppedOut: Boolean;
  end;

  TList = record
    head, tail: ptr;
  end;

var
  list: TList;
  playersAmount, k, i, counter: integer;
  curr: ptr;
  flag: Boolean;

procedure addNewEl(var curr: ptr);
begin
  if list.head = nil then
  begin
    list.head := curr;
    list.tail := curr;
    curr.next := nil;
    curr.prev := nil;
    exit;
  end;

  curr.next := nil;
  curr.prev := list.tail;
  list.tail.next := curr;
  list.tail := curr;
end;

procedure createList(playersAmount: integer);
var
  {curr,} tmp: ptr;
  i: integer;

begin
  list.head := nil;
  list.tail := nil;
  new(curr);

  for i := 1 to playersAmount do
  begin
    tmp := curr;
    new(curr);
    curr.numberOfPlayer := i;
    curr.droppedOut := false;
    addNewEl(curr);
    tmp.next := curr;
    curr.prev := tmp;
  end;

end;

procedure kickPlayer;                  //??????????? ????
var
  i: integer;

begin
  curr := list.head;
  for I := 1 to k - 1 do
  begin
    curr := curr.next;
    if curr = list.tail then
    begin
      curr := list.head;
    end;
  end;
  curr.droppedOut := true;
  writeln(curr.numberOfPlayer,' ');
end;

begin
  counter := 1;
  flag := true;
  writeln('??????? ?????????? ???????');
  Readln(playersAmount);
  writeln('? ????????? ????????? ?????? k-??? ?????, ??????? ?');
  Readln(k);
  writeln('---------------------------------------------------');
  createList(playersAmount);
  //curr := list.head;

  while flag do
  begin
    if (counter = k) then
    begin
      kickPlayer;
      counter := 1;
    end;
    inc(counter);
  end;

  Readln;

end.
