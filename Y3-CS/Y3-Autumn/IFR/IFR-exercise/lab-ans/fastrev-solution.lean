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
-- the time complexity is therefore O(n^2)

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

namespace ex07
open revDefn
/- --- Do not add/change anything above this line --- -/

/-
However we would like to show that fast rev and rev do the same thing.

You'll need to establish a lemma about revaux (hint: try writing
down a precise specification of what revaux does).

You may use the fact that lists with ++ form a monoid 
-/

lemma list_rneutral : ∀ as : list A, as ++ [] = as :=
begin
  assume as,
  induction as with a as' ih,
  refl,
  --
  dsimp [(++)],
  dsimp [(++)] at ih,
  rewrite ih,
end


lemma l2 : ∀ a : A, ∀ as bs : list A, 
  as ++ (a :: bs) = (snoc as a) ++ bs :=
begin
  assume a as,
  induction as with a' as' ih,
  assume bs,
  refl,
  -- 
  assume bs,
  dsimp [snoc],
  rewrite ih,
end



-- how to prove revaux as [] = (rev as) ++ []
-- we have  revaux as bs = (rev as) ++ bs
lemma l1 : ∀ as bs : list A, revaux as bs = (rev as) ++ bs :=
begin
  assume as,
  induction as with a as' ih,
  assume bs,
  refl,
  -- 
  assume bs,
  dsimp [revaux],
  -- we can rewrite ih, because it is for all bs
  rewrite ih,
  -- rev as' ++ (a :: bs) = rev (a :: as') ++ bs
  dsimp [rev],
  -- assuming (rev as') is a list, we need to show
  -- as ++ (a :: bs) = (snoc as a) ++ bs
  rewrite l2,
end


theorem fastrev_thm : ∀ as : list A , fastrev as = rev as :=
begin
  assume as,
  dsimp [fastrev],
  -- what is revaux?
  -- revaux as bs is essentially the same as (rev as) ++ bs
  -- revaux as bs = (rev as) ++ bs
  have x : revaux as nil = (rev as) ++ [],
  apply l1,
  -- need to show [] is right neutral
  rewrite list_rneutral at x,
  exact x,
end


--- Do not add/change anything below this line ---
end ex07