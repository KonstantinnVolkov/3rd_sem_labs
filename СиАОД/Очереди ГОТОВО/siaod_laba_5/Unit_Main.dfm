object Form1: TForm1
  Left = 0
  Top = 0
  Caption = 'Form1'
  ClientHeight = 566
  ClientWidth = 649
  Color = clBtnFace
  Font.Charset = DEFAULT_CHARSET
  Font.Color = clWindowText
  Font.Height = -11
  Font.Name = 'Tahoma'
  Font.Style = []
  OldCreateOrder = False
  OnCreate = FormCreate
  DesignSize = (
    649
    566)
  PixelsPerInch = 96
  TextHeight = 13
  object LabelTickLength: TLabel
    Left = 193
    Top = 23
    Width = 39
    Height = 23
    Alignment = taRightJustify
    Caption = #1058#1072#1082#1090
    Font.Charset = DEFAULT_CHARSET
    Font.Color = clWindowText
    Font.Height = -19
    Font.Name = 'Tahoma'
    Font.Style = []
    ParentFont = False
  end
  object LabelThinkingTime: TLabel
    Left = 8
    Top = 23
    Width = 96
    Height = 33
    Alignment = taRightJustify
    AutoSize = False
    Caption = #1047#1072#1076#1077#1088#1078#1082#1072
    Font.Charset = DEFAULT_CHARSET
    Font.Color = clWindowText
    Font.Height = -19
    Font.Name = 'Tahoma'
    Font.Style = []
    ParentFont = False
  end
  object LabelAnswer: TLabel
    Left = 324
    Top = 108
    Width = 50
    Height = 23
    Caption = #1050#1055#1044': '
    Font.Charset = DEFAULT_CHARSET
    Font.Color = clWindowText
    Font.Height = -19
    Font.Name = 'Tahoma'
    Font.Style = []
    ParentFont = False
  end
  object Memo1: TMemo
    Left = 8
    Top = 62
    Width = 239
    Height = 139
    Lines.Strings = (
      '3'
      '1 2 3'
      '3 3 4 5 6 4 2 1 5'
      '4 2 5 6 2 1 1 3 3'
      '4 4 5 6 6 2 1 1 3'
      '1 1 4 4 2 2 4 3 3'
      '4 5 1 3 3 3 2 2 4'
      '4 5 1 1 1 3 3 3 2')
    ReadOnly = True
    ScrollBars = ssVertical
    TabOrder = 0
  end
  object ButtonRun: TButton
    Left = 8
    Top = 207
    Width = 94
    Height = 25
    Caption = #1057#1090#1072#1088#1090
    TabOrder = 1
    OnClick = ButtonRunClick
  end
  object ListBox1: TListBox
    Left = 8
    Top = 270
    Width = 633
    Height = 256
    Anchors = [akLeft, akTop, akRight, akBottom]
    Font.Charset = RUSSIAN_CHARSET
    Font.Color = clWindowText
    Font.Height = -19
    Font.Name = 'Consolas'
    Font.Style = []
    ItemHeight = 22
    ParentFont = False
    ScrollWidth = 20000
    TabOrder = 2
    ExplicitWidth = 580
  end
  object Edit1: TEdit
    Left = 245
    Top = 23
    Width = 33
    Height = 21
    TabOrder = 3
    OnChange = Edit1Change
  end
  object Edit2: TEdit
    Left = 126
    Top = 23
    Width = 33
    Height = 21
    TabOrder = 4
    OnChange = Edit2Change
  end
  object Button1: TButton
    Left = 1038
    Top = 30
    Width = 75
    Height = 25
    Caption = #1055#1086#1096#1072#1075#1086#1074#1086
    TabOrder = 5
    Visible = False
    OnClick = Button1Click
  end
end
