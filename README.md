# jceksreader


To Build
--------

```
mvn clean install
```

To Run
------

- Copy the JCEKS secret from kubernetes cluster using

```
kubectl cp <NAMESPACE>/<PODNAME>:/jceks/..data/secrets.jceks secrets.jceks

Example:
kubectl cp warehouse-1572892161-gfh4/metastore-0:/jceks/..data/secrets.jceks secrets.jceks
```

- Run JCEKSReader to extract the metastore credentials

```
java -cp target/jceks-reader-1.0-SNAPSHOT.jar JCEKSReader ./secrets.jceks
```
