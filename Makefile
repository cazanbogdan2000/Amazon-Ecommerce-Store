rulare1:
	java -cp TEMA\ 2.jar Main < rulare1.txt > rulare1.out

rulare2:
	java -cp TEMA\ 2.jar Main < rulare2.txt > rulare2.out

rulare3:
	java -cp TEMA\ 2.jar Main < rulare3.txt > rulare3.out

rulare4:
	java -cp TEMA\ 2.jar Main < rulare4.txt > rulare4.out

rulare5:
	java -cp TEMA\ 2.jar Main < rulare5.txt > rulare5.out

clean:
	rm -r *.out store_* binary.dat


