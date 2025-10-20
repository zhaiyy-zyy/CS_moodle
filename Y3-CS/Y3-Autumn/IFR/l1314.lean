/- 
  Lecture 13 & 14: 
    1) Algebra and Order
    2) Decidability
-/

import tactic -- need to install mathlib

namespace l1314
set_option pp.structure_projections false
-- use the natural number
open nat


-- Addition
def add : ℕ → ℕ → ℕ 
| n zero     := n
| n (succ m) := succ (add n m)

local notation (name := add) m + n := add m n

-- Properties of addition

-- 0 is neutral, 0 + x = x = x + 0
theorem add_rneutr : 
  ∀ n : ℕ, n + 0 = n :=sorry

theorem add_lneutr : 
  ∀ n : ℕ, 0 + n  = n :=sorry

-- Addition is associative, x + y + z = x + (y + z)
theorem add_assoc : 
  ∀ l m n : ℕ , (l + m) + n = l + (m + n) :=sorry

-- Addition is commutative, x + y = y + x
theorem add_comm : 
  ∀ m n : ℕ , m + n = n + m :=sorry


-- Multiplication
def mul : ℕ → ℕ → ℕ 
| m 0         := 0
| m (succ n ) := (mul m n) + m

local notation (name := mul) m * n := mul m n


-- Properties of Multiplication

-- 1 is neutral, 1 * n = n = n * 1
theorem mult_rneutr : 
  ∀ n : ℕ, n * 1 = n :=sorry

theorem mult_lneutr : 
  ∀ n : ℕ, 1 * n  = n :=sorry

-- Multiplication is associative 
theorem mult_assoc : 
  ∀ l m n : ℕ , (l * m) * n = l * (m * n) :=sorry

-- Multiplication is commutative 
theorem mult_comm : 
  ∀ m n : ℕ , m * n = n * m :=sorry

-- Distributivity laws
theorem mult_distr_l : ∀ l m n : ℕ , 
  (m + n) * l = m * l + n * l :=sorry

theorem mult_distr_r :  ∀ l m n : ℕ , 
  l * (m + n) = l * m + l * n :=sorry


-- What's next? exponential
def exp : ℕ → ℕ → ℕ 
| n zero      := 1
| n (succ m)  := (exp n m) * n
local notation (name := exp) m^n := exp m n


-- binomial
theorem binom : ∀ x y : ℕ, 
  (x + y)^2 = x^2 + 2*x*y + y^2 :=
begin
  assume x y,
  dsimp[exp],
  -- 1*(x+y)*(x+y) = 1*x*x+2*x*y+1*y*y
  calc
    1*(x+y)*(x+y) = (1*x + 1*y)*(x+y) : by rewrite mult_distr_r 1
    ... = (1*x + 1*y)*x + (1*x + 1*y)*y : by rewrite mult_distr_r
    ... = 1*x*x + 1*y*x + (1*x + 1*y)*y: by rewrite mult_distr_l
    ... = 1*x*x + 1*y*x + (1*x*y + 1*y*y): by rewrite mult_distr_l
    ... = 1*x*x + 1*y*x + 1*x*y + 1*y*y: by rewrite <- add_assoc
    ... = 1*x*x + (1*y*x + 1*x*y) + 1*y*y : by rewrite add_assoc (1*x*x)
    ... = 1*x*x + (1*(y*x)+1*x*y) + 1*y*y : by rewrite mult_assoc 1 y x
    ... = 1*x*x + (1*(x*y)+1*x*y) + 1*y*y : by rewrite mult_comm x y
    ... = 1*x*x + (1*(x*y)+1*(x*y)) + 1*y*y : by rewrite mult_assoc 1 x y
    ... = 1*x*x + ((1+1)*(x*y)) + 1*y*y : by rewrite <- mult_distr_l
    ... = 1*x*x + 2*(x*y) + 1*y*y : by reflexivity
    ... = 1*x*x + 2*x*y + 1*y*y : by rewrite <- mult_assoc 2 x y,
end


-- binomial
/-
theorem binom : ∀ x y : ℕ, (x + y)^2 = x^2 + 2*x*y + y^2 :=
begin
  assume x y,
  ring,
end 
-/


/-
 Monoid:
 A set S and a binary opertion f : S x S → S, is a monoid if:
 1) f: S x S → S is associative
 2) There is a neutral element s, such that 
              ∀ x : S, f s x = x
              ∀ x : S, f x s = x

 Commutative monoid: monoid + commutative

 Semiring:
 Given the fact that 
 1) ℕ, + is a communative monoid, and
 2) ℕ, * is a monoid
 3) * distribute over +

 The sturcture of ℕ with + and * is called a semiring

 why semi? we don't have additive inverse 

 Ring
 semiring with additive inverse, i.e., a unary operator (-)
 x + (-x) = 0
 requires negative numbers, i.e., ℤ 

 Field
 Ring with the inverse of multiplication, i.e, a unary opertor (⁻¹)
 x * (x⁻¹) = 1
 requires rational number

 Group
 1) Associative
 2) Identity: has a neutral element
 3) Inverse element

-/


-- Order
def le (m n : ℕ ): Prop :=
  ∃ k : ℕ, k + m = n
-- local notation
local notation (name := le) m ≤ n := le m n 

-- first example, prove 2 is less than or equal to 3
example : 2 ≤ 3 :=
begin
  dsimp [le],
  existsi 1,
  refl,
end

-- second example, prove it is not the case that 3 is less than or equal to 2
example : ¬ (3 ≤ 2) :=
begin
  dsimp [le],
  assume h,
  cases h with k h',
  -- k + 3 = 2 is equivalent to succ (k+2) = succ (1)
  have x : k + 2 = 1,
  injection h',
  -- similarly, succ (k + 1) = succ 0
  have x' : k + 1 = 0,
  injection x,
  -- we know that succ k ≠ 0
  cases x',
end

-- What are the property of ≤ (order theory)?
/-
  1) relfexivity
    ∀ n : ℕ, n ≤ n
  2) transitivity 
    ∀ l m n : ℕ, l ≤ m → m ≤ n → l ≤ n
  3) antisymmetry
    ∀ m n : ℕ, m ≤ n → n ≤ m → m = n 
-/

-- relfexivity
theorem le_refl : ∀ n : ℕ, n ≤ n :=
begin
  assume n,
  dsimp [le],
  existsi 0,
  rewrite add_lneutr,
end

-- transitivity
theorem le_trans : ∀ l m n : ℕ , l ≤ m → m ≤ n → l ≤ n :=
begin
  dsimp [le],
  assume l m n lm mn,
  cases lm with k h,
  cases mn with k' h',
  -- what natural number should we choose?
  existsi k + k',
  rewrite <- h',
  rewrite <- h,
  rewrite add_comm k,
  rewrite add_assoc,
end

-- antisymmetry, leave this as an excercise
theorem anti_sym : ∀ m n : ℕ, m ≤ n → n ≤ m →  m = n :=
begin
  sorry,
end


/-
  If a relation satisfies the followings, then it is partial ordered
    1) Reflexivity
    2) Transitivity
    3) Antisymmetry

  Is there any other partial order realtions?
-/


-- How to define less than based on le
def lt(m n :ℕ) : Prop :=
  m + 1 ≤ n 
local notation (name := lt) m < n := lt m n

-- What are the properties of <？
/-
  1) Reflexivity?
  2) Transitivity?
  3) Antisymmetry?
-/

-- is it reflexivity 
-- ∀ n :ℕ, ¬ (n < n)

-- is it transitive
-- ∀ l m n : ℕ, l < m → m < n → l < n 

-- is it symmetry: asymmetry 
-- ∀ m n : ℕ, m < n →  n < m → false


/-
  if ≤ is partial order, then is ≥ 
  Equality =
-/


-- Decidability
/-
  We say that a problem is decidable if there exists a machine that 
  gives the correct answer for every statement in the domain of the problem.

  What we mean by "can be implemented in a computer"
  ~ something is decidable

  "A relation is decidable"
-/

-- A relation Equality between m and n
def Eq_nat (m n : ℕ) : Prop
:= m = n 

#check Eq_nat

-- A function that decide if two natural numbers are equivalent
def eq_nat : ℕ → ℕ → bool
| zero zero := tt
| (succ m) zero := ff
| zero (succ n) := ff
| (succ m) (succ n) := eq_nat m n


-- complete : whenever it is true, I say yes
lemma eq_nat_compl : 
∀ m n : ℕ , Eq_nat m n → eq_nat m n = tt :=
begin
  dsimp [Eq_nat],
  assume m,
  -- we want to keep n here, as the inductive hypothesis 
  -- should work for all n rather than a particular n
  induction m with m' ih,
  assume n h,
  -- replace n with 0
  rewrite <- h,
  -- by definition
  refl,
  -- inductive step, prove with inductive hypothesis
  assume n h,
  -- now, ih works for all n
  -- replace n with succ m'
  rewrite <- h,
  -- by definition, we remove succ for both parameters
  dsimp[eq_nat],
  -- we could apply ih, when n = m'
  apply ih,
  refl,
end




-- sound, if I say yes it is true
lemma eq_nat_sound : 
  ∀ m n : ℕ , eq_nat m n = tt → Eq_nat m n :=
begin
  dsimp[Eq_nat],
  -- assumptions on m n h before induction is a bit too special,
  -- we want it to be more general
  --assume m n h,
  assume m,
  induction m with m' ih,
  -- induction n with n' ih2, we could do induction with n, but a little 
  -- bit overkill. We only need case analysis here
  assume n h,
  cases n with n',
  refl,
  dsimp [eq_nat] at h,
  cases h,
  assume n h,
  cases n with n',
  dsimp [eq_nat] at h,
  cases h,
  dsimp [eq_nat] at h,
  apply congr_arg,
  apply ih,
  exact h,
end

-- What do we mean by  Equality is deciable?
theorem eq_nat_ok : 
  ∀ m n : ℕ , Eq_nat m n ↔ eq_nat m n = tt :=
begin
  dsimp[Eq_nat],
  assume m n,
  constructor,
  apply eq_nat_compl,
  apply eq_nat_sound,
end

-- eq_nat decides Eq_nat, ie the equality.
-- equality on natural numbers is decidable.
/-
A predicate PP : A → Prop is decidable
if there is a function p : A → bool such that
∀ a : A, PP a ↔ p a = tt

Prime : ℕ → Prop is decidable
≤ : ℕ → ℕ → Prop is decidable

Halt : Program → Prop
Halt p = program will stops
-/

def Tricky (f : ℕ → bool) : Prop :=
  ∀ n : ℕ, f n = tt 
-- Tricky is not decidable


-- end
end l1314