unit Unit_Main;

interface

uses
  Winapi.Windows, Winapi.Messages, System.SysUtils, System.Variants, System.Classes, Vcl.Graphics,
  Vcl.Controls, Vcl.Forms, Vcl.Dialogs, Vcl.StdCtrls, Vcl.ComCtrls, Math;

type
    TArrStr = Array of String;

    TPClient = ^TClient;

    TClient = Record
        TaskLengths: Array of Integer;
        TaskToDo, RemainingTimeToThink: Integer;
    End;

    TLineOfSamePriority = Record
        Clients: Array of TPClient;
        ClientToWorkWith: Integer;
    End;

    TClientBase = Array of TLineOfSamePriority;


  TForm1 = class(TForm)
    LabelTickLength: TLabel;
    LabelThinkingTime: TLabel;
    Memo1: TMemo;
    ButtonRun: TButton;
    ListBox1: TListBox;
    LabelAnswer: TLabel;
    Edit1: TEdit;
    Edit2: TEdit;
    Button1: TButton;
    procedure TrackBarThinkingTimeChange(Sender: TObject);
    procedure TrackBarTickLengthChange(Sender: TObject);
    procedure FormCreate(Sender: TObject);
    procedure ButtonRunClick(Sender: TObject);
    procedure Button1Click(Sender: TObject);
    procedure Edit1Change(Sender: TObject);
    procedure Edit2Change(Sender: TObject);
  private
    { Private declarations }
  public
    procedure DrawTick(Pri, CliNumInPri, PointsMade: Integer);
    function GetRealNumFromPriorityAndClientNum(Pri, CliNumInPri: Integer) : Integer;
    procedure DoTick();
    function CheckIfClientCanWork(Prior, ClientNum: Integer): Boolean;
    procedure SubstractWaitingTime(Pri, CliNumInPri, PointsMade: Integer);
  end;

procedure MyMessageBoxInfo(Form: TForm; CaptionWindow, TextMessage: String; IsWarning: Boolean = False); external 'Dll_MyMessageBox.dll';
function FindRegEx(SInput, StrRegEx: String; StrIfNothingFound: String = '')
  : TArrStr; external 'FindRegExes.dll';

var
  Form1: TForm1;
  AllClients: TClientBase;
  ThinkingTime, TickLen, QuantityOfClients, QuantityOfTicks, PointsDoneByProcessor: Integer;

implementation

{$R *.dfm}

function TForm1.CheckIfClientCanWork(Prior, ClientNum: Integer): Boolean;
var
    Client: TPClient;

begin
    Client := AllClients[Prior].Clients[ClientNum];
    Result := (Client.TaskToDo <> -1)
        and (Client.RemainingTimeToThink = 0);
end;

function TForm1.GetRealNumFromPriorityAndClientNum(Pri, CliNumInPri: Integer) : Integer;
var
    RealNumOfClient, i: Integer;

begin
    RealNumOfClient := 0;
    for i := 0 to Pri - 1 do
        RealNumOfClient := RealNumOfClient + Length(AllClients[i].Clients);
    Result := RealNumOfClient + CliNumInPri;
end;

procedure TForm1.DrawTick(Pri, CliNumInPri, PointsMade: Integer);
var
    RealNumOfWorking, RealNumTemp, i, j, k, TimeInThoughts: Integer;
    Cl: TPClient;

begin
    if (Pri > -1) and (CliNumInPri > -1) and (PointsMade > -1) then
    begin
        RealNumOfWorking := GetRealNumFromPriorityAndClientNum(Pri, CliNumInPri);
        for I := 1 to PointsMade do
            ListBox1.Items[RealNumOfWorking] := ListBox1.Items[RealNumOfWorking] + IntToStr(AllClients[Pri].Clients[CliNumInPri].TaskToDo + 1);
        TimeInThoughts := Min(TickLen - PointsMade, ThinkingTime);
        for I := 1 to TimeInThoughts do
            ListBox1.Items[RealNumOfWorking] := ListBox1.Items[RealNumOfWorking] + '*';
        for I := PointsMade + TimeInThoughts + 1 to TickLen do
            ListBox1.Items[RealNumOfWorking] := ListBox1.Items[RealNumOfWorking] + '_';
        ListBox1.Items[RealNumOfWorking] := ListBox1.Items[RealNumOfWorking] + '|';
    end
    else
        RealNumOfWorking := -1;

    for I := Low(AllClients) to High(AllClients) do
        for j := Low(AllClients[i].Clients) to High(AllClients[i].Clients) do
        begin
            Cl := AllClients[i].Clients[j];
            RealNumTemp := GetRealNumFromPriorityAndClientNum(i, j);
            if RealNumTemp <> RealNumOfWorking then
            begin
                TimeInThoughts := Min(Min(cl.RemainingTimeToThink, ThinkingTime), TickLen);
                for k := 1 to TimeInThoughts do
                    ListBox1.Items[RealNumTemp] := ListBox1.Items[RealNumTemp] + '*';
                for k := TimeInThoughts + 1 to TickLen do
                    ListBox1.Items[RealNumTemp] := ListBox1.Items[RealNumTemp] + '_';
                ListBox1.Items[RealNumTemp] := ListBox1.Items[RealNumTemp] + '|';
            end;
        end;
end;

procedure TForm1.Edit1Change(Sender: TObject);
begin
   TickLen:= strtoint(Edit1.Text)
end;

procedure TForm1.Edit2Change(Sender: TObject);
begin
    ThinkingTime := strtoint(Edit2.Text);
end;

procedure TForm1.SubstractWaitingTime(Pri, CliNumInPri, PointsMade: Integer);
var
    i, j: Integer;
    Cl: TPClient;

begin
    for I := Low(AllClients) to High(AllClients) do
        for j := Low(AllClients[i].Clients) to High(AllClients[i].Clients) do
        begin
            Cl := AllClients[i].Clients[j];
            if (Pri = i) and (CliNumInPri = j) then
            begin
                if cl.TaskLengths[cl.TaskToDo] = 0 then
                    Cl.RemainingTimeToThink := Max(ThinkingTime - TickLen + PointsMade, 0)
            end
            else
            begin
                if Cl.RemainingTimeToThink > 0 then
                    Cl.RemainingTimeToThink := Max(0, Cl.RemainingTimeToThink - TickLen);
            end;
        end;
end;

procedure TForm1.DoTick();
var
    Pri, Cli_In_Pri, PointsDoneWithCli, j: Integer;
    ClientToWorkWithIsNotFound, NoneToWorkInThisPri, NotGivenOrderToNextInThisPri: Boolean;
    Cli: TPClient;

begin
    Inc(QuantityOfTicks);
    ClientToWorkWithIsNotFound := True;
    Pri := Low(AllClients);

    while (Pri <= High(AllClients)) and ClientToWorkWithIsNotFound do
    begin
        if AllClients[Pri].ClientToWorkWith <> -1 then
        begin
            Cli_In_Pri := AllClients[Pri].ClientToWorkWith;
            while (Cli_In_Pri <= High(AllClients[Pri].Clients))
                    and ClientToWorkWithIsNotFound do
            begin
                ClientToWorkWithIsNotFound := not CheckIfClientCanWork(Pri, Cli_In_Pri);
                Inc(Cli_In_Pri);
            end;

            if ClientToWorkWithIsNotFound then
                Cli_In_Pri := 0;

            while (Cli_In_Pri < AllClients[Pri].ClientToWorkWith)
                    and ClientToWorkWithIsNotFound do
            begin
                ClientToWorkWithIsNotFound := not CheckIfClientCanWork(Pri, Cli_In_Pri);
                Inc(Cli_In_Pri);
            end;
        end;

        Inc(Pri);
    end;

    if not ClientToWorkWithIsNotFound then
    begin
        Dec(Pri);
        Dec(Cli_In_Pri);
        Cli := AllClients[Pri].Clients[Cli_In_Pri];
        PointsDoneWithCli := Min(Cli.TaskLengths[Cli.TaskToDo], TickLen);
        Cli.TaskLengths[Cli.TaskToDo] := Cli.TaskLengths[Cli.TaskToDo] - PointsDoneWithCli;

        // Draw and Wait
        Form1.DrawTick(Pri, Cli_In_Pri, PointsDoneWithCli);
        SubstractWaitingTime(Pri, Cli_In_Pri, PointsDoneWithCli);
        PointsDoneByProcessor := PointsDoneByProcessor + PointsDoneWithCli;

        NoneToWorkInThisPri := False;
        if Cli.TaskLengths[Cli.TaskToDo] = 0 then
        begin
            Inc(Cli.TaskToDo);
            if Cli.TaskToDo > High(Cli.TaskLengths) then
            begin
                Cli.TaskToDo := -1;
                NoneToWorkInThisPri := True;
                j := Low(AllClients[Pri].Clients);
                while (j <= High(AllClients[Pri].Clients))
                        and NoneToWorkInThisPri do
                begin
                    NoneToWorkInThisPri := AllClients[Pri].Clients[j].TaskToDo = -1;
                    Inc(j);
                end;
                if NoneToWorkInThisPri then
                    AllClients[Pri].ClientToWorkWith := -1;
            end;
        end;

        if not NoneToWorkInThisPri then
        begin
            AllClients[Pri].ClientToWorkWith := Cli_In_Pri + 1;
            if AllClients[Pri].ClientToWorkWith > High(AllClients[Pri].Clients) then
                AllClients[Pri].ClientToWorkWith := 0;
            NotGivenOrderToNextInThisPri := False;
        end;
    end
    else
    begin
        Form1.DrawTick(-1, -1, -1);
        SubstractWaitingTime(-1, -1, -1);
    end;
end;

procedure TForm1.Button1Click(Sender: TObject);
var
    ThereIsNothingToDo: Boolean;
    i: Integer;
    PointsWastes: Integer;

begin
    DoTick();

    ThereIsNothingToDo := True;
    i := 0;
    while ThereIsNothingToDo and (i < Length(AllClients)) do
    begin
        ThereIsNothingToDo := AllClients[i].ClientToWorkWith = -1;
        Inc(i);
    end;

    if ThereIsNothingToDo then
    begin
        PointsWastes := TickLen * QuantityOfTicks;
        LabelAnswer.Caption := LabelAnswer.Caption + IntToStr(PointsDoneByProcessor) + ' / ' + IntToStr(PointsWastes) + ' = 0.' + IntToStr(Trunc(1000 * PointsDoneByProcessor/PointsWastes));
    end;
end;

procedure TForm1.ButtonRunClick(Sender: TObject);
var
    ThereIsNothingToDo: Boolean;
    i: Integer;
    PointsWastes: Integer;

begin
    ButtonRun.Enabled := False;
    Button1.Enabled := False;

    repeat
        DoTick();

        ThereIsNothingToDo := True;
        i := 0;
        while ThereIsNothingToDo and (i < Length(AllClients)) do
        begin
            ThereIsNothingToDo := AllClients[i].ClientToWorkWith = -1;
            Inc(i);
        end;
    until ThereIsNothingToDo;

    PointsWastes := TickLen * QuantityOfTicks;
    LabelAnswer.Caption := LabelAnswer.Caption + IntToStr(PointsDoneByProcessor) + ' / ' + IntToStr(PointsWastes);
    if PointsDoneByProcessor/PointsWastes < 1 then
        LabelAnswer.Caption := LabelAnswer.Caption + ' = 0.' + IntToStr(Trunc(1000 * PointsDoneByProcessor/PointsWastes))
    else
        LabelAnswer.Caption := LabelAnswer.Caption + ' = 1';

end;

procedure TForm1.FormCreate(Sender: TObject);
var
    i, j, k, realNumOfClient: Integer;
    StrArr1, StrArr2: TArrStr;

begin
    SetLength(AllClients, StrToInt(FindRegEx(Memo1.Lines[0], '\d+')[0]));
    StrArr1 := FindRegEx(Memo1.Lines[1], '\d+');
    RealNumOfClient := 0;
    ThinkingTime := 1;
    TickLen := 1;
    QuantityOfTicks := 0;
    PointsDoneByProcessor := 0;

    for I := Low(AllClients) to High(AllClients) do
    begin
        SetLength(AllClients[i].Clients, StrToInt(StrArr1[i]));

        AllClients[i].ClientToWorkWith := 0;

        for J := Low(AllClients[i].Clients) to High(AllClients[i].Clients) do
        begin
            New(AllClients[i].Clients[j]);

            AllClients[i].Clients[j].TaskToDo := 0;
            AllClients[i].Clients[j].RemainingTimeToThink := 0;

            StrArr2 := FindRegEx(Memo1.Lines[RealNumOfClient + 2], '\d+');
            SetLength(AllClients[i].Clients[j].TaskLengths, Length(StrArr2));
            for k := Low(AllClients[i].Clients[j].TaskLengths)
                    to High(AllClients[i].Clients[j].TaskLengths) do
            begin
                AllClients[i].Clients[j].TaskLengths[k] := StrToInt(StrArr2[k]);
            end;

            ListBox1.Items.Add(' ' + IntToStr(RealNumOfClient + 1) + ': ');
            Inc(RealNumOfClient);
        end;
    end;

    QuantityOfClients := realNumOfClient;
end;

procedure TForm1.TrackBarThinkingTimeChange(Sender: TObject);
begin
    ThinkingTime := strtoint(Edit1.Text);
end;

procedure TForm1.TrackBarTickLengthChange(Sender: TObject);
begin
    TickLen :=  strtoint(Edit2.Text);
end;

end.
