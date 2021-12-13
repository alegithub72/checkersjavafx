;This file will be executed next to the application bundle image
;I.e. current directory will contain folder cinvaders with application files
[Setup]
AppId={{sa.fx.draugths}}
AppName=cinvaders
AppVersion=4.0
AppVerName=cinvaders 4.0
AppPublisher=sagame
AppComments=cinvaders
AppCopyright=Copyright (C) 2021
;AppPublisherURL=http://java.com/
;AppSupportURL=http://java.com/
;AppUpdatesURL=http://java.com/
DefaultDirName={localappdata}\cinvaders
DisableStartupPrompt=Yes
DisableDirPage=Yes
DisableProgramGroupPage=Yes
DisableReadyPage=Yes
DisableFinishedPage=Yes
DisableWelcomePage=Yes
DefaultGroupName=sagame
;Optional License
LicenseFile=
;(Windows 2000/XP/Server 2003 are no longer supported.)
MinVersion=6.0
OutputBaseFilename=cinvaders-x64bit-4.0
Compression=lzma
SolidCompression=yes
PrivilegesRequired=lowest
SetupIconFile=cinvaders\cinvaders.ico
UninstallDisplayIcon={app}\cinvaders.ico
UninstallDisplayName=cinvaders
WizardImageStretch=No
WizardSmallImageFile=cinvaders-setup-icon.bmp   
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
