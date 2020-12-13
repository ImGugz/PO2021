#!/bin/zsh
r=0

GREEN="\e[32m"
NOCOLOR="\033[0m"
RED="\033[1;31m"
YELLOW="\e[33m"

for x in auto-tests/*.in; do
    if [ -e $x:r.import ]; then
        java -cp $CLASSPATH -Dimport=$x:r.import -Din=$x -Dout=$x:r.outhyp woo.app.App
    else
        java -cp $CLASSPATH                      -Din=$x -Dout=$x:r.outhyp woo.app.App
    fi

    diff -cBb -w $x:r.out $x:r.outhyp > $x:r.diff
    if [ -s $x:r.diff ]; then
        r=$(($r+1))
        echo "${RED}FAIL${NOCOLOR}: $x. See file $x:r.diff"
    else
        echo "${GREEN}PASSED${NOCOLOR}: $x"
        rm -f $x:r.diff $x:r.outhyp
    fi
done

rm *.woo
echo -e "${YELLOW}$(((93-$r)*100/93))% tests passed${NOCOLOR}"
echo "Done."