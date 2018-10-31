
# Pub/Sub + Apache Beam（Cloud DataFlow）でログコレクターの例

モダンなサーバレスアーキテクチャのログ収集基盤である、Pub/SubとDataFlowのコンビネーションなのです。  

GoogleのDataFlowのSDK version 1.XがEnd of Lifeなるということで、プログラムの移行及び、streaming処理について記述します。  

## ローカルで開発するときにの注意点
 - 1. JDKはOracle JDKの1.8を使う
 - 2. Java互換の言語が型の推論に失敗する用になるので、一部Javaで記述する必要がある
  
## Pub/Subとは
push. pull方式で記す

## DataFlowのstreaming処理方法
windowで定期的にスキャンしている

## パイプラインのSDK 1.Xからのシンタックスの変更部分

### 2. appengineの動作設定
refere : https://cloud.google.com/appengine/docs/flexible/python/writing-and-responding-to-pub-sub-messages

