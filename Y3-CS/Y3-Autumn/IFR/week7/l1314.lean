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

-- exponential
def exp: ℕ → ℕ → ℕ :=
sorry

-- we want to use ^ as the local notation
local notation (name := exp) m^n := exp m n

-- binomial
theorem binom : ∀ x y : ℕ, (x + y)^2 = x^2 + 2*x*y + y^2 :=
begin
  sorry
end 

-- Order
def le (m n : ℕ ): Prop :=
  sorry

-- local notation
local notation (name := le) m ≤ n := le m n 

-- first example, prove 2 is less than or equal to 3
example : 2 ≤ 3 :=
begin
  sorry,
end

-- second example, prove it is not the case that 3 is less than or equal to 2
example : ¬ (3 ≤ 2) :=
begin
  sorry,
end

-- What are the property of ≤ (order theory)?

-- How to define less than
def lt(m n :ℕ) : Prop :=
  sorry

local notation (name := lt) m < n := lt m n

-- What are the properties of less than？

-- A different definition of le
def le' (m n : ℕ): Prop := sorry

/- Is there any other partial order realtions?
  1) Reflexivity
  2) Transitivity
  3) Antisymmetry
-/

-- Decidability

-- A relation between m and n
def Eq_nat (m n : ℕ) : Prop := sorry

-- A function that decide if two natural numbers are equivalent
def eq_nat : ℕ → ℕ → bool := sorry

-- How to prove Equality is deciable 

-- end
end l1314