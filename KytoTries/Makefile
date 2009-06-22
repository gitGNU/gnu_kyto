all:generated/IdentityTaskMap.java

generated/IdentityTaskMap.java: slice/kytotries.ice
	slice2freezej --output-dir generated/KytoTries \
	--ice --dict IdentityTaskMap,Ice::Identity,KytoTries::Task  \
	--index idxId,KytoTries::Task,parent \
	-I/usr/share/slice/ $<
	mv generated/KytoTries/IdentityTaskMap.java generated/KytoTries/IdentityTaskMap.java.tmp
	echo package KytoTries\;>generated/KytoTries/IdentityTaskMap.java
	cat generated/KytoTries/IdentityTaskMap.java.tmp >> generated/KytoTries/IdentityTaskMap.java
	rm generated/KytoTries/IdentityTaskMap.java.tmp
	
