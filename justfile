[linux]
lab2:
    #!/usr/bin/env bash
    set -euo pipefail
    javac src/main/java/xyz/whinyaan/dcit50/lab2/*.java -Xdiags:verbose
    java -Dawt.useSystemAAFontSettings=lcd_hrgb -Dswing.aatext=true -cp src/main/java xyz.whinyaan.dcit50.lab2.App

[macos]
lab2:
    #!/usr/bin/env bash
    set -euo pipefail
    javac src/main/java/xyz/whinyaan/*.java -Xdiags:verbose
    java -cp src/main/java xyz.whinyaan.App

[windows]
lab2:
    #!powershell.exe
    javac src/main/java/xyz/whinyaan/*.java -Xdiags:verbose
    java -cp src/main/java xyz.whinyaan.App