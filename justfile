[linux]
run:
    #!/usr/bin/env bash
    set -euo pipefail
    javac src/main/java/xyz/whinyaan/dcit50/lab2/*.java -Xdiags:verbose
    java -Dawt.useSystemAAFontSettings=lcd_hrgb -Dswing.aatext=true -cp src/main/java xyz.whinyaan.dcit50.lab2.App

[macos]
run:
    #!/usr/bin/env bash
    set -euo pipefail
    javac src/main/java/xyz/whinyaan/*.java -Xdiags:verbose
    java -cp src/main/java xyz.whinyaan.App

[windows]
run:
    #!powershell.exe
    javac src/main/java/xyz/whinyaan/*.java -Xdiags:verbose
    java -cp src/main/java xyz.whinyaan.App