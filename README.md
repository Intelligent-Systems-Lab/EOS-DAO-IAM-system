# EOS-DAO-IAM-system

```shell= 
sh -c "echo \"alias cleos='/usr/bin/cleos -u https://api.testnet.eos.io'\" >> ~/.bashrc"
source ~/.bashrc
```

```shell= 
keosd &

cleos wallet list
cleos wallet create -n tony --file tony.txt

cleos wallet import -n tony
# paste your private key
```


```shell= 
git clone https://github.com/Intelligent-Systems-Lab/EOS-DAO-IAM-system.git

mkdir -p EOS-DAO-IAM-system/ipfs_test/build

cd EOS-DAO-IAM-system/ipfs_test/build

cmake .. && make
```