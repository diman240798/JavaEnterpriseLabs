@echo off

set BINDIR=%~dp0\bin
set APP=nanicky.getpetshome-1.0-SNAPSHOT
set LOGDIR=%~dp0\log
set LOGCFG=%~dp0\%APP%.log.xml

java -Dlogback.configurationFile="%LOGCFG%" -Dpa.log.dir="%LOGDIR%" -cp %BINDIR%\%APP%.jar ru.sfedu.nanicky.shop.Main
pause