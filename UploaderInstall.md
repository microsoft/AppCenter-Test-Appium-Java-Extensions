# XTC Command Line Interface (CLI)

Command line interface for XTC.

## Installation

### Windows

1. Download ```xtc.win7-x64.zip``` from the [last stable build](http://calabash-ci.macminicolo.net:8080/view/Uploader/job/Uploader%20master/lastSuccessfulBuild/artifact/publish/Release/xtc.win7-x64.zip).
   
   After downloading, right-click on the Zip file, select "Unblock", and click the "OK" button.
2. Unzip the file.

### OS X
1. Download ```xtc.osx.10.10-x64.tar.gz``` from the [last stable build](http://calabash-ci.macminicolo.net:8080/view/Uploader/job/Uploader%20master/lastSuccessfulBuild/artifact/publish/Release/xtc.osx.10.10-x64.tar.gz).

2. Unpack the archive file to any directory:

   ```tar -xvzf xtc.osx.10.10-x64.tar.gz```

   This will create a `xtc` directory.

3. Add the `xtc` directory created above to the PATH environment variable.

## Usage
```
xtc help
Usage: xtc <command> [options]
Available commands:
  help            Print help for a command
  run             Run external command extension
  test            Upload tests to the Test Cloud
```
