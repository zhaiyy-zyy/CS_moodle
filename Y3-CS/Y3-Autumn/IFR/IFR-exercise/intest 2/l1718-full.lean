/-
Lecture 17-18 : naturality and sort
-/

namespace l1718
set_option pp.structure_projections false
variables {A B C D: Type}

-- cons in another direction
def snoc : list A → A → list A
| []  a := [a]
| (a :: as) b := a :: (snoc as b)

-- Our definition of reverse function
def rev : list A → list A
| [] := []
| (a :: as) := snoc (rev as) a

/-
  Functor: a type which allows us to apply functions without 
  changing the general structure of it
-/

-- Definition of the map function
def map_list : (A → B) → list A → list B 
| f [] := []
| f (a :: as) := (f a) :: (map_list f as)

/-
two basic ingredients
1) identity
id : A → A

2) composition
comp : (B → C) → (A → B) → A → C
-/
def id : A → A 
| a := a

def comp : (B → C) → (A → B) → A → C
| g f a := g (f a)
local notation (name := comp) f ∘ g := comp f g

/-
Simple properties 
  1) id ∘ f = f
  2) f ∘ id = f 
  3) (f ∘ g) ∘ h = f ∘ (g ∘ h)
-/
theorem idl : ∀ f : A → B, id ∘ f = f := sorry

theorem idr : ∀ f : A → B, f ∘ id = f := sorry

theorem assoc : ∀ f : A → B, ∀ g : B → C, ∀ h : C → D,
        h ∘ g ∘ f = h ∘ (g ∘ f) := sorry

/-  
is this a monoid? why not
it is a category
monoind: one object categories.

However, this isn’t really a monoid because there isn’t one fixed type 
on which the operations are defined but a whole family of types. 
This structure is called a category, which is the basic notion in the 
field of category theory.
-/

/-
To make list a functor, we need to show two properties:
map_list id = id
  Given a list, if we apply id on each single element in this list, the 
  resulting list should be exactly the same as the original one
map_list (g ∘ f) = map_list g ∘ map_list f
  Apply the composition of two functions g and f on each element of the given
  list, should be equivalent to do the mapping first and then the composition
 -/

-- map_list id = id 

/-
How to prove two functions are equal?
f g : A → B
if two function f g are equal, then for all possible a : A, 
the output should be the same
∀ a : A, f a = g a → f = g
funext
-/

/-
I am writing @id A instead of just id because I need to tell lean 
that I want to use the type variable A here, so I have to give 
it explicitly. @id is id with the implicit parameter A : Type 
made explicit.
-/
theorem map_id : map_list (@id A) = id :=
begin
  apply funext,
  assume as,
  induction as with a as' ih,
  dsimp [map_list, id],
  refl,
  -- map_list id (a :: as') = a :: as'
  -- (id a) :: (map_list id as') = a :: as'
  -- a :: (map_list id as') = a :: as'
  dsimp [map_list, id],
  rewrite ih,
  dsimp [id],
  refl,
end

-- map_list (g ∘ f) = map_list g ∘ map_list f
theorem map_comp : ∀ g : B → C, ∀ f : A → B,
  map_list (g ∘ f) = (map_list g) ∘ (map_list f) := sorry



-- naturality of rev
/-
  We have the map_list which can map a function to a list:
  (A → B) → list A → list B

  We know the reverse will reverse the order of the elements in a list 
  
  What if we do 

  map_list f (ref l)?

  Is it similar to do map_list first then reverse?

  rev (map_list f l)

  Here give a concrete example:
  A: ℕ, B: bool, f: ℕ → bool (isEven)

  map_list f (rev [1,2,4]) = map_list f [4,2,1] = [ff,ff,tt]
  rev (map_list f [1,2,4]) = rev ([tt,ff,ff]) = [ff,ff,tt]

  This property of rev is called naturality.

  What is it? natural means it works on all types uniformly.

-/

-- rev is natural, excercise
theorem nat_rev : ∀ f : A → B, ∀ l : list A,
  map_list f (rev l) = rev (map_list f l) :=
begin
  assume f,
  assume l,
  induction l with l l' ih,
  dsimp[rev],
  dsimp[map_list],
  dsimp[rev],
  refl,
  dsimp[map_list],
  dsimp[rev],
  dsimp[snoc],
  rw ih,
end 


/-
  Is there any function which is not natural?
-/

open nat

-- recall our definition of less than or equal
def le (m n : ℕ): Prop :=
  ∃ k : ℕ, k + m = n
local notation (name := le) m ≤ n := le m n


/-
 Given the definition of ≤ on bool
-/
def leb : ℕ → ℕ → bool 
| 0 n := tt
| (succ m) 0 := ff
| (succ m) (succ n) := leb m n

/-
  Show it is decidable. This is cw3
-/
-- Complete
lemma Le2leb : ∀ m n : ℕ, m ≤ n → leb m n = tt := sorry
-- Sound
lemma leb2Le : ∀ m n : ℕ, leb m n = tt → m ≤ n := sorry

/-
We have so far covered the relation ≤, and the basic concepts of list.
What can we do next? 

sort
 : list ℕ → list ℕ 
sort [6,3,8,2,3] = [2,3,4,6,8] 

How do we sort a list
-/

open list

/- definition of if-then-else -/
/-
  How to define if then else?
  if bool, then do something, else do something.
  Based on the result of the given bool, we need to make the choice.
  bool → A → A → A
-/
def ite : bool → A → A → A
| tt a b := a
| ff a b := b
local notation (name := ite) 
  if x then a else b := ite x a b

/-
  insert
-/
def insert : ℕ → list ℕ → list ℕ 
| n [] := [n]     -- easy to insert an element into an empty list
| n (m :: ns) :=  -- we compare the value of the given element and the
                  -- first element in the list
    if (leb n m) then n :: m :: ns  -- n is added at front if n ≤ m
                 else m :: (insert n ns) -- otherwise, insert n into the tail

#eval insert 6 [2,3,3,8]

/-
  Definition of the insertion sort
  we insert an element into a sorted list
-/
def sort : list ℕ → list ℕ 
| [] := []
| (n :: ns) := insert n (sort ns) -- we have to insert the element

#eval sort [6,3,8,2,3] -- sanity check


/-
we want to prove that sort sorts! (Verify it)
what do we mean by sorting?
  Sorted : list ℕ → Prop  
the first element ≤ the second ≤ the third ...
-/

-- An element is less than or equal to the first element in the list
inductive Le_list : ℕ → list ℕ → Prop 
| le_nil : ∀ n : ℕ , Le_list n [] -- this proposition is true if the given list is empty
| le_cons : ∀ m n : ℕ, ∀ ms : list ℕ,
    n ≤ m → Le_list n (m :: ms) -- or if we know n ≤ m, and m is the head of a list

/-
  Given the ns is sorted, and n is less than or equal to the first
  element in ns, then we could have n :: ns is also sorted

  We use inductive definitions of predicates:
  The basic idea is that we state some rules how to prove the predicate
-/
inductive Sorted : list ℕ → Prop
| sorted_nil : Sorted []
| sorted_cons : ∀ n : ℕ, ∀ ns : list ℕ, 
    Sorted ns → Le_list n ns → Sorted (n :: ns)

open Le_list Sorted -- need to open the inductive definition before using them


-- Excercise
lemma leb_reverse : ∀ m n, leb m n = ff → leb n m = tt :=
begin
  sorry,
end

-- an excercise
lemma leinsert : ∀ m n : ℕ, ∀ ns: list ℕ, 
  m ≤ n → Le_list m ns →  Le_list m (insert n ns):=
begin
  assume m n ns h1 h2,
  cases ns with n' ns',
  -- Le_list m (insert n nil) = Le_list m [n]
  dsimp [insert],
  -- to prove it, we need to apply le_cons
  apply le_cons,
  assumption,
  -- Le_list m (insert n (n' :: ns'))
  -- insert n (n' :: ns') = if leb n n' then n::n'::ns' 
  --                        else n' :: insert n ns'
  dsimp [insert],
  cases x : (leb n n'),
  dsimp [ite],
  apply le_cons,
  -- we could cases h2 to extra information
  -- le_nil : ∀ n : ℕ , Le_list n [] 
  -- le_cons :∀ m n : ℕ, ∀ ms : list ℕ, n ≤ m → Le_list n (m :: ms)
  -- we don't need to name n in le_nil and m n ms in le_cons
  cases h2 with _ _ _ _ h3,
  assumption,
  --
  dsimp [ite],
  apply le_cons,
  assumption,
end


lemma insert_lem : ∀ ns : list ℕ, ∀ n : ℕ,
  Sorted ns → Sorted (insert n ns) :=
begin
  assume ns n h,
  -- induction on lists
  induction ns with n' ns' ih,
  -- Sorted (insert n nil) = Sorted [n]
  dsimp [insert],
  -- we need to show [n] is sorted, what we can do
  -- from the definition Sorted ns → Le_list n ns → Sorted(n :: ns)
  -- Sorted [] → Le_list n [] → Sorted [n]
  apply sorted_cons,
  --apply sorted_nil,
  exact h,
  apply le_nil,
  --
  -- we are trying to insert n into n' :: ns"
  -- we need to compare if n ≤ n', leb n n' return a bool
  -- what we can do with a bool? cases
  -- Sorted (insert n (n' :: ns')) = if (leb n n') then (n::n'::ns') else (n' :: insert n ns')
  dsimp [insert],
  cases hh : (leb n n'),
  dsimp [ite],
  -- how to prove sorted? apply sorted_cons?
  -- to show n' :: insert n ns' is sorted, we need to show 
  -- 1) insert n ns' is sorted,
  -- 2) and Le_list n' (insert n ns')
  apply sorted_cons,
  apply ih,
  -- we could do cases here, why? Sorted (n' :: ns') is defined inductively
  -- it only matches the cons case:
  -- ∀ n : ℕ, ∀ ns : list ℕ, Sorted ns → Le_list n ns → Sorted (n :: ns)
  -- cases gives us 4 parts, a ℕ (n'), a list ℕ (ns'), Sorted ns'
  -- and Le_list n' ns'
  -- we don't need to name n' and ns' here, therefore we use _ 
  cases h with _ _ h_le h_s, 
  assumption,
  -- to prove le_list, we need to apply either le_nil or le_cons
  -- insert n ns' is definitely not nil
  -- ∀ m n : ℕ, ∀ ms : list ℕ, n ≤ m → Le_list n (m :: ms)
  -- the problem is that we don't have the head of insert n ns'
  -- Here, we need a lemma:
  -- ∀ m n : ℕ, ∀ ns: list ℕ, m ≤ n → Le_list m ns →  Le_list m (insert n ns)
  apply leinsert,
  -- we don't have n'≤ n, but we have leb n n' ff
  apply leb2Le,
  -- we need to have the reverse
  apply leb_reverse,
  exact hh,
  -- similarly, we can extract information from Sorted (n' :: ns')
  cases h with _ _ h1 h2,
  assumption,
  -- the rest could be proved similarly
  dsimp [ite],
  apply sorted_cons,
  assumption,
  apply le_cons,
  apply leb2Le,
  assumption,
end


/-
  To verify it, we need to show a given list is well-sorted.
-/
theorem sort_sorts : ∀ ns : list ℕ ,
  Sorted (sort ns) :=
begin
  assume ns,
  -- how to prove lists
  induction ns with n' ns' ih,
  -- Sorted (sort nil) = Sorted nil
  dsimp [sort],
  -- Sorted nil is the first rule for proving Sorted
  apply sorted_nil,
  -- Sorted (sort (n' :: ns')) = Sorted (insert n' (sort ns'))
  dsimp [sort],
  -- Sorted (insert n' (sort ns')), we need a lemma
  -- Sorted ns → Sorted (insert n ns)
  apply insert_lem,
  assumption,
end


/-
What else?
We also need to show the sorted list contains the exactly 
the same elements as as in the original list.
This is called Permutation.
Perm : list A → list A → Prop
Perm [2,3,1,4] [1,2,3,4]
¬ Perm [2,3] [2,1,3]
We need to show the following property
∀ l: list ℕ, Perm l (sort l)
-/


/-
The insert relaiton
-/
inductive Insert : A → list A → list A → Prop 
-- insert at the head using cons
| insert_hd : ∀ a:A,∀ as : list A,Insert a as (a :: as)
-- insert someware else
| insert_tl : ∀ a b : A, ∀ bs abs : list A, 
      Insert a bs abs 
    → Insert a (b::bs) (b::abs)

/- 
How to define the permutation relationship
-/ 
inductive Perm : list A → list A →  Prop
| perm_nil : Perm [] []      -- empty list is the permutation of the empty list
| perm_cons : ∀ a : A, ∀ as bs bs': list A,
  -- if as is the permutation of bs, then insert an a in an arbitrarily position
  -- is equivalent to add the a using cons 
              Perm as bs → Insert a bs bs' → Perm (a :: as) bs'  

open Insert Perm


-- reflexivity
theorem refl_perm : ∀ as : list A, Perm as as :=
begin
  sorry,
end 

-- show insert is a special case of Insert
lemma insert_insert : ∀ n : ℕ, ∀ ns: list ℕ, 
      Insert n ns (insert n ns):=
begin
  assume n ns,
  induction ns with n' ns' ih,
  -- Insert n nil (insert n nil)
  -- Insert n nil [n]
  dsimp [insert], -- this is exactly what insert at head does
  apply insert_hd,
  -- Insert n (n' :: ns') (insert n (n' :: ns'))
  -- Insert n (n' :: ns') (if (leb n n') then n::n'::ns'
  --                       else n':: insert n ns')
  dsimp [insert],
  -- case analysis
  cases x: (leb n n'),
  dsimp [ite], -- Insert n (n' :: ns') n':: insert n ns'
  -- this is insert into the tail
  -- Insert n ns' (insert n ns')
  apply insert_tl,
  exact ih,
  -- Insert n (n' :: ns') (n::n'::ns')
  dsimp [ite],
  -- this is insert at head
  apply insert_hd,
end


theorem perm_sort : ∀ l: list ℕ, Perm l (sort l) :=
begin
  assume l,
  induction l with a l' ih,
  dsimp[sort],
  apply perm_nil,
  -- Perm (a :: l') (sort (a :: l'))
  -- Perm (a :: l') (insert a (sort l'))
  dsimp[sort],
  -- Perm as bs → Insert a bs bs' → Perm (a :: as) bs' 
  -- Perm (a :: l') (insert a (sort l'))
  -- Perm l' ?
  apply perm_cons,
  exact ih,
  -- ? = (sort l')
  -- Insert a (sort l') (insert a (sort l'))
  -- for ℕ, Insert relation holds for the function insert
  -- insert generate a special case of Insert 
  apply insert_insert,
end


end l1718

