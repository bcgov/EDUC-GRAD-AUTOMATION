@echo off
setlocal ENABLEEXTENSIONS
set JAVA_MINIMUM_RUNTIME=1.8
set KEY_NAME="HKLM\SOFTWARE\JavaSoft\Java Runtime Environment"
set VALUE_NAME=CurrentVersion
set ARGS=%*
::
:: get the current version
::
FOR /F "usebackq skip=2 tokens=3" %%A IN (`REG QUERY %KEY_NAME% /v %VALUE_NAME% 2^>nul`) DO (
    set ValueValue=%%A
)
if defined ValueValue (
    if %JAVA_MINIMUM_RUNTIME% gtr %ValueValue% (
        @echo The current Java runtime is %ValueValue% and needs to be greater than %JAVA_MINIMUM_RUNTIME%.
    ) else (
        :: run the application
        java -jar D:\Education_Projects\GRAD\EDUC-GRAD-TEST-AUTOMATION\backend\grad-trax-test-suite\target\grad-trax-test-suite.jar %ARGS%
    )
) else (
    @echo You need to install java version %JAVA_MINIMUM_RUNTIME% or greater to run this application.
    goto end
)
:end