/- 
  Lecture 13 & 14: 
    1) Algebra and Order
    2) Decidability
-/

-- import tactic -- need to install mathlib

namespace l1314
set_option pp.structure_projections false
-- use the natural number
open nat
/-
-- definition of addition
def add :  ℕ → ℕ → ℕ
| n zero := n 
| n (succ m) := succ (add n m)

--Properties of addition

--0 is neutral
theorem add_lneutr:
∀ n : ℕ , 0 + n = n := sorry

theorem add_rneutr:
∀ n : ℕ , n + 0 = n := sorry

--commutative
theorem add_comm:
∀ m n : ℕ, m + n = n + m := sorry

--associative
theorem add_assoc:
∀ l m n : ℕ, l + m + n = l + (m + n) := sorry

--multplication
def mult : ℕ → ℕ → ℕ 
| n zero := zero
| n (succ m) := (mult n m) + n 

--properties of multiplication
theorem mult_lneutr:
∀ n : ℕ, 1 * n = n := sorry

theorem mult_rneutr:
∀ n : ℕ, n * 1 = n := sorry

--commutative
theorem mult_comm:
∀ m n : ℕ, m * n = n * m := sorry

--associative
theorem amult_assoc:
∀ l m n : ℕ, l * m * n = l * (m * n) := sorry

--distributivity
theorem mult_distr_l:
∀ l m n : ℕ, (m * n) * l = l * m + l * n := sorry

theorem mult_distr_r:
∀ l m n : ℕ, l * (m * n) = l * m + l * n := sorry
-/
/--/
-- exponential (指数 n^0=1,n^(m+1)=n*n^m)
def exp: ℕ → ℕ → ℕ :=
| n zero := 1
| n (succ m) := (exp n m) * n
-- we want to use ^ as the local notation
local notation (name := exp) m^n := exp m n
-/
-- binomial

theorem binom : ∀ x y : ℕ, (x + y)^2 = x^2 + 2*x*y + y^2 :=
begin
  assume x y,
end 


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
  dsimp[le],
  assume h,
  cases h with k h,
  have h' : 2 + k = 1,
  injection h,
  have h'' : k + 1 = 0,
  injection h',
  cases h'',
end

-- What are the property of ≤ (order theory)?

-- How to define less than
def lt(m n :ℕ) : Prop :=
  ∃ k : ℕ, k + m = n

local notation (name := lt) m < n := lt m n

-- What are the properties of less than？

-- A different definition of le
def le' (m n : ℕ): Prop := 
  ∃ k : ℕ, m + k = n
/- Is there any other partial order realtions?
  1) Reflexivity
  2) Transitivity
  3) Antisymmetry
-/

-- Decidability

-- A relation between m and n
def Eq_nat (m n : ℕ) : Prop := 
 m = n 
-- A function that decide if two natural numbers are equivalent
def eq_nat : ℕ → ℕ → bool
| zero zero := tt
| zero (succ m) := ff
| (succ m) zero := ff
| (succ m) (succ n) := eq_nat m n 
-- How to prove Equality is deciable
theorem eq_ok: 
 ∀ m n : ℕ, Eq_nat m n <-> (eq_nat m n) = tt := 

-- end
end l1314