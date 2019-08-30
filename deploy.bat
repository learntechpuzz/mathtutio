@echo off
set ApplicationName=mathtutio
set StageName=dev
set S3WebSiteBucketName=mathtutio
set S3CloudFormationBucketName=mathtutio-cloudformation
set S3AppDataBucketName=mathtutio-data
set InputFile=template.yaml
set OutputFile=template-output.yaml
set StackName=%ApplicationName%
set SourceEmail=mathtutio@gmail.com

:: Build app-lambda-functions-java
cd lambda-functions\app-lambda-functions-java
call mvn clean package
cd ..\..\

:: Delete S3 CloudFormation Bucket
call aws s3 rb s3://%S3CloudFormationBucketName% --force

:: Create S3 CloudFormation Bucket
call aws s3 mb s3://%S3CloudFormationBucketName%

:: Copy swagger.yaml into S3 CloudFormation Bucket
call aws s3 cp swagger.yaml s3://%S3CloudFormationBucketName%/swagger.yaml

:: Package cloud formation template
call aws cloudformation package --template-file %InputFile% --output-template-file %OutputFile% --s3-bucket %S3CloudFormationBucketName%

:: Deploy cloud formation template
call aws cloudformation deploy --template-file %OutputFile% --stack-name %StackName% --parameter-overrides ApplicationName=%ApplicationName% StageName=%StageName% S3WebSiteBucketName=%S3WebSiteBucketName% S3CloudFormationBucketName=%S3CloudFormationBucketName% S3AppDataBucketName=%S3AppDataBucketName% SourceEmail=%SourceEmail% --capabilities CAPABILITY_NAMED_IAM

:: Build application web-site

cd web-site

:: Configure application web-site logo

call aws cloudformation describe-stacks --stack-name %StackName% --query "Stacks[0].Outputs[?OutputKey=='CognitoUserPoolId'].OutputValue" --output text > CognitoUserPoolId.txt
set /p user_pool_id=<CognitoUserPoolId.txt

call aws cloudformation describe-stacks --stack-name %StackName% --query "Stacks[0].Outputs[?OutputKey=='CognitoAppClientId'].OutputValue" --output text > CognitoAppClientId.txt
set /p app_client_id=<CognitoAppClientId.txt

call aws cognito-idp set-ui-customization --user-pool-id %user_pool_id% --client-id %app_client_id% --image-file fileb://./public\logo.png

:: Replace CognitoAuthLoginURL in public.html

set search=CognitoAuthLoginURL
call aws cloudformation describe-stacks --stack-name %StackName% --query "Stacks[0].Outputs[?OutputKey=='CognitoAuthLoginURL'].OutputValue" --output text > CognitoAuthLoginURL.txt
set /p replace=<CognitoAuthLoginURL.txt

powershell "(Get-Content public\public.html) | Foreach-Object {$_ -replace '%search%', '%replace%'} | Set-Content public\public.html"

:: Replace S3WebsiteURL in secured.html

set search=S3WebsiteURL
call aws cloudformation describe-stacks --stack-name %StackName% --query "Stacks[0].Outputs[?OutputKey=='S3WebsiteURL'].OutputValue" --output text > S3WebsiteURL.txt
set /p replace=<S3WebsiteURL.txt

powershell "(Get-Content public\secured.html) | Foreach-Object {$_ -replace '%search%', '%replace%'} | Set-Content public\secured.html"

:: Replace APIGatewayURL in axios-api.js

set search=APIGatewayURL
call aws cloudformation describe-stacks --stack-name %StackName% --query "Stacks[0].Outputs[?OutputKey=='APIGatewayURL'].OutputValue" --output text > APIGatewayURL.txt
set /p replace=<APIGatewayURL.txt

powershell "(Get-Content src\modules\common\axios\axios-api.js) | Foreach-Object {$_ -replace '%search%', '%replace%'} | Set-Content src\modules\common\axios\axios-api.js"

:: Replace APIGatewayURL in axios-cognito.js

set search=CognitoAuthAPIURL
call aws cloudformation describe-stacks --stack-name %StackName% --query "Stacks[0].Outputs[?OutputKey=='CognitoAuthAPIURL'].OutputValue" --output text > CognitoAuthAPIURL.txt
set /p replace=<CognitoAuthAPIURL.txt

powershell "(Get-Content src\modules\common\axios\axios-cognito.js) | Foreach-Object {$_ -replace '%search%', '%replace%'} | Set-Content src\modules\common\axios\axios-cognito.js"

:: Replace APIGatewayURL in Header.js

set search=CognitoAuthLogoutURL
call aws cloudformation describe-stacks --stack-name %StackName% --query "Stacks[0].Outputs[?OutputKey=='CognitoAuthLogoutURL'].OutputValue" --output text > CognitoAuthLogoutURL.txt
set /p replace=<CognitoAuthLogoutURL.txt

powershell "(Get-Content src\modules\header\components\Header.js) | Foreach-Object {$_ -replace '%search%', '%replace%'} | Set-Content src\modules\header\components\Header.js"


call npm install
call npm run build
call npm run deploy

:: Copy public html
call aws s3 cp public/public.html s3://%S3WebSiteBucketName%/public.html

:: Copy secured html
call aws s3 cp public/secured.html s3://%S3WebSiteBucketName%/secured.html

:: Copy logo
call aws s3 cp public/logo.png s3://%S3WebSiteBucketName%/logo.png

cd ..