# Encrypt_Decrypt
##sub heading
This project is about Encryption &amp; Decryption using Command Line in java.


To use this code, go to the  `Ecrypt_Decrypt` Directory: 

- Compile the Main : 
```java
 javac Main.java
```
You ll see in the `Ecrypt_Decrypt`  `.class` of all classes.

- To run it you have several options : 

We pass the parameters to the command line for instance
> - **`-alg`** : To choose the which algorithm; we've `shift` or `unicode`. if we didn't specifie by default it will use `shift` Algorirthm.
> - **`-mode`**: To choose the mode; `enc` or `dec`. If we didn't specifie by default it will use `enc` mode.
> - **`-data`** : To set the data to encrypt or decrypt.
> - **`-key`** : To set the key to encrypt or decrypt.  If we didn't specifie by default it will use `0` as a key.
> - **`-in`** : To set the Input file to read data from which will be encrypt or decrypt. If we've both `-in` and `-data` we choose `-data` over `-in`.
> - **`-out`** : To set the Output file to write the result. If we don't have both `-out` we print the result to the standard output.

> As you will see in the examples the order of the parameters is not important.

Examples:

```java
java -cp . Main `-alg` unicode -mode enc -key 5  -in input.txt -out protected.txt 
```

```java
java -cp . Main -mode ENC -data "Hello There!" -out protected.txt -key 5 
```

```java
java -cp . Main -mode dec -in protected.txt -out decrypted.txt -key 5 -alg shift
```

```java
java -cp . Main -mode ENC -data "Hello There!"  -key 5 -alg unicode
```
