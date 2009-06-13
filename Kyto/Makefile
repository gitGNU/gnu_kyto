all:generated/IdentityTaskMap.java generated/StringTaskDBDescription.java

generated/IdentityTaskMap.java: slice/Kyto.ice
	slice2freezej --output-dir generated/KYTO \
	--ice --dict IdentityTaskMap,Ice::Identity,Kyto::TaskDescription  \
	-I/usr/share/slice/ $<
	mv generated/KYTO/IdentityTaskMap.java generated/KYTO/IdentityTaskMap.java.tmp
	echo package KYTO\;>generated/KYTO/IdentityTaskMap.java
	cat generated/KYTO/IdentityTaskMap.java.tmp >> generated/KYTO/IdentityTaskMap.java
	rm generated/KYTO/IdentityTaskMap.java.tmp
	
	
generated/StringTaskDBDescription.java: slice/Kyto.ice
	slice2freezej --output-dir generated/KYTO \
	--ice --dict StringTaskDBDescription,string,Kyto::TaskDBDescription  \
	-I/usr/share/slice/ $<	
	mv generated/KYTO/StringTaskDBDescription.java generated/KYTO/StringTaskDBDescription.java.tmp
	echo package KYTO\;>generated/KYTO/StringTaskDBDescription.java
	cat generated/KYTO/StringTaskDBDescription.java.tmp >> generated/KYTO/StringTaskDBDescription.java
	rm generated/KYTO/StringTaskDBDescription.java.tmp