# prometheus-linuxsys-exporter
An exporter for prometheus, to get data of processes running in Linux.

## 升级
- RemoteExecute单例模式，维持连接。如果连接断了，本次不取值重建。
- 增加nethogs探针。
