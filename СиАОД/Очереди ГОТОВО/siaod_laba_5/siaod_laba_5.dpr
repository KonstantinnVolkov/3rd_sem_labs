program siaod_laba_5;

uses
  Vcl.Forms,
  Unit_Main in 'Unit_Main.pas' {Form1};

{$R *.res}

begin
  Application.Initialize;
  Application.MainFormOnTaskbar := True;
  Application.CreateForm(TForm1, Form1);
  Application.Run;
end.
