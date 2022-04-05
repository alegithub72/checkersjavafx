;This file will be executed next to the application bundle image
;I.e. current directory will contain folder cinvaders with application files
[Setup]
AppId={{sa.fx.draugths}}
AppName=cinvaders
AppVersion=4.1
AppVerName=cinvaders 4.1
AppPublisher=sagame
AppComments=cinvaders
AppCopyright=Copyright (C) 2021
;AppPublisherURL=http://java.com/
;AppSupportURL=http://java.com/
;AppUpdatesURL=http://java.com/
UsePreviousAppDir=yes
DefaultDirName={autopf}\cinvaders
DisableStartupPrompt=Yes
DisableDirPage=yes
DisableProgramGroupPage=Yes
DisableReadyPage=Yes
DisableFinishedPage=Yes
DisableWelcomePage=no 
DefaultGroupName=sagame
;Optional License
;LicenseFile=LICENSE
;(Windows 2000/XP/Server 2003 are no longer supported.)
MinVersion=6.0
OutputBaseFilename=cinvaders-x64bit-4.1
DiskSpanning=yes
SlicesPerDisk=1
DiskSliceSize=24000000
Compression=lzma2/ultra64
SolidCompression=yes
PrivilegesRequired=lowest
SetupIconFile=cinvaders\cinvaders.ico 
;SetupIconFile=wizard-setup.ico
UninstallDisplayIcon={app}\cinvaders.ico
UninstallDisplayName=cinvaders
WizardImageStretch=Yes
WizardSmallImageFile=cinvaders-setup-icon.bmp
WizardStyle=modern
;WizardImageFile=startScreen2.bmp
ArchitecturesInstallIn64BitMode=x64


[Languages]
Name: "english"; MessagesFile: "compiler:Default.isl"

[Files]
Source: "cinvaders\cinvaders.exe"; DestDir: "{app}"; Flags: ignoreversion
Source: "cinvaders\*"; DestDir: "{app}"; Flags: ignoreversion recursesubdirs createallsubdirs

[Icons]
Name: "{group}\cinvaders"; Filename: "{app}\cinvaders.exe"; IconFilename: "{app}\cinvaders.ico"; Check: returnTrue()
Name: "{commondesktop}\cinvaders"; Filename: "{app}\cinvaders.exe";  IconFilename: "{app}\cinvaders.ico"; Check: returnFalse()


[Run]
Filename: "{app}\cinvaders.exe"; Parameters: "-Xappcds:generatecache"; Check: returnFalse()
Filename: "{app}\cinvaders.exe"; Description: "{cm:LaunchProgram,cinvaders}"; Flags: nowait postinstall skipifsilent; Check: returnTrue()
Filename: "{app}\cinvaders.exe"; Parameters: "-install -svcName ""cinvaders"" -svcDesc ""cinvaders"" -mainExe ""cinvaders.exe""  "; Check: returnFalse()

[UninstallRun]
Filename: "{app}\cinvaders.exe "; Parameters: "-uninstall -svcName cinvaders -stopOnUninstall"; Check: returnFalse()

[Code]
function returnTrue(): Boolean;
begin
  Result := True;
end;

function returnFalse(): Boolean;
begin
  Result := False;
end;

function InitializeSetup(): Boolean;
begin
// Possible future improvements:
//   if version less or same => just launch app
//   if upgrade => check if same app is running and wait for it to exit
//   Add pack200/unpack200 support? 
  Result := True;
end;  
