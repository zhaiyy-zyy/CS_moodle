/-
COMP2068-IFR Exercise
(fast reverse) 

Our goal is to prove that fast reverse computes the same function
as reverse.

-/

open list
set_option pp.structure_projections false
variables {A B C : Type}
namespace revDefn

/- 
In the lecture we have introduced the function reverse
(and the auxilliary function snoc)
-/

def snoc : list A → A → list A
| [] a := [a]
| (a :: as) b := a :: (snoc as b)

def rev : list A → list A 
| [] := []
| (a :: as) := snoc (rev as) a


-- if we are doing reverse on [1,2,3]
-- rev([1,2,3]) = snoc (rev [2,3]) 1 : we take out the first element
-- then we need to do the rev for substring...
-- the time complexity is therefore O(n^2), as snoc has complexity of O(n)

/- 
Our implementation of rev is inefficient: it has O(n^2) complexity.
The definition below (called fastrev) is efficient, having only O(n) complexity.
It uses an auxillary function revaux which accumulates the reversed 
list in a 2nd variable.
-/

def revaux : list A → list A → list A
| [] bs := bs
| (a :: as) bs := revaux as (a :: bs)

def fastrev (l : list A) : list A
  := revaux l []

#reduce fastrev [1,2,3]


end revDefn

namespace fastrev
open revDefn
/- --- Do not add/change anything above this line --- -/

/-
However we would like to show that fast rev and rev do the same thing.

You'll need to establish a lemma about revaux (hint: try writing
down a precise specification of what revaux does).

You may use the fact that lists with ++ form a monoid 
-/



theorem fastrev_thm : ∀ as : list A , fastrev as = rev as :=
begin
  sorry,
end


--- Do not add/change anything below this line ---
end fastrev