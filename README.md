# Chicken Nugget Problem Algorithm

For a description of the Chicken Nugget Problem see https://artofproblemsolving.com/wiki/index.php/Chicken_McNugget_Theorem

This algorithm is a generalized solution to the Chicken Nugget problem. It takes a set of positive integers as inputs
and returns the largest integer that cannot be obtained by a linear combination of the inputs.

The basic approach is to find the set of linear combinations of the input values that together map the entire set of 
positive integers. To do this we start with the minimum input value which maps all multiples of that integer.

For example, for the set of inputs `[6 9 20]`, the smallest input is 6. We know that we can obtain values of
all multiples of 6 with linear combinations of this set of inputs.

Next we need to find linear combinations that map the set of integers from 0 (always mapped by the minimum input
value) to the minumum input value minus 1. Using the same example inputs that set would be `#{0 1 2 3 4 5}`.

To find linear combinations that satisfy these required factors we can navigate a set of linear combinations sequentially
and for each one calculate the modulus relative to the minimum input value. We remove modulus values from the set of
required factors until we have found the last required factor and we use the associated linear combination to calculate 
the highest integer that cannot be obtained by a linear combination of the inputs.

For example, using the inputs `[6 9 20]` the next linear combination after 6 is 9. 9 mod 6 is 3. So we can obtain any
multiple of 6 starting with 9, e.g. 9 + 6x. Each of these values is at an offset of 3 relative to the closest multiple
of 6. So beginning with 9, we've mapped all positive integers 6x and 6x + 3.

The next linear combination that satisfies a different required factor is 20. 20 mod 6 is 2. So beginning with 20 we've
mapped all positive integers 6x + 2.

Similarly for:
```
29 % 6 = 5
40 % 6 = 4
49 % 6 = 1
```

Since 49 satisfies the last required factor we can simlpy subtract the minimum input value, in this case 6, to get
the final answers which for this set of inputs which is 43.

The solution works as follows:

1. Get the minimum input value.

2. Create a set of `required-factors` containing all integers from 0 to the minimum input value minus 1. 

3. Create a set of linear combinations of the inputs. The number of linear combinations is arbitrarily limited
due to resource and time constraints. Consequently this algorithm will not work for large input values.

4. Loop through the linear combinations in sequential order. For each linear combination, calculate the modulus relative
to the minimum input value. Remove the modulus value if it is in the list of `required-factors`. If it was the last
item in the `required-factors` list then subtract the minimum input value from the associated linear combination to
receive the final answer.