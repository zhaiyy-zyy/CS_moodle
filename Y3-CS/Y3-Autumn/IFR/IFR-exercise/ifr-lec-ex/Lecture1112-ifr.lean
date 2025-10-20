
namespace ifr
set_option pp.structure_projections false
open nat

/-
inductive nat : Type
| zero : nat
| succ : nat → nat
-/


example : ∀ n : ℕ, 0 ≠ succ n :=
begin
    sorry,
end 



theorem inj_succ : ∀ m n : nat, succ m = succ n → m = n :=
begin
    sorry,
end

/-
def double : ℕ → ℕ
|  
| 

#reduce (double 4)
  
def half : ℕ → ℕ 
| 
|


theorem half_double : ∀ n : ℕ, half (double n) = n := 
begin
    sorry,
end
-/

/-
def add : ℕ → ℕ → ℕ  
| 
| 
-/


theorem add_rneutr : ∀ n : ℕ, n + 0 = n :=
begin
    sorry,
end 

theorem add_lneutr : ∀ n : ℕ, 0 + n = n :=
begin
    sorry,
end 

theorem add_assoc : ∀ l m n : ℕ, (l + m) + n = l + (m + n) :=
begin
    sorry,
end


theorem add_comm : ∀ m n : ℕ, m + n = n + m :=
begin
    sorry,
end


--def mul : ℕ → ℕ → ℕ
--| 
--| 
 


end ifr
