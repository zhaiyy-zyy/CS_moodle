
namespace bool

/-
inductive bool : Type
| ff : bool
| tt : bool
-/


--#reduce ff && (tt || ff)
--#reduce tt && (tt || ff)


/-
def bnot : bool → bool 
| tt := 
| ff := 

def band : bool → bool → bool
| 
| 

def bor : bool → bool → bool
| 
|
-/

example : ∀ x : bool, x = tt ∨ x = ff :=
begin
    sorry,
end

def is_tt : bool → Prop
| ff := false
| tt := true

theorem cons : tt ≠ ff :=
begin
    sorry,
end



theorem distr_b : ∀ x y z : bool, 
    x && (y || z) = x && y || x && z :=
begin
    sorry,
end


theorem dm1_b : ∀ x y : bool, bnot(x || y) = bnot x && bnot y :=
begin
    sorry,
end


theorem dm2_b : ∀ x y : bool, bnot(x && y) = bnot x || bnot y :=
begin
    sorry,
end




theorem and_thm : ∀ x y : bool, is_tt x ∧ is_tt y ↔ is_tt (x && y) :=
begin
    sorry,
end

theorem not_thm : ∀ x : bool, ¬ (is_tt x) ↔ is_tt(bnot x) :=
begin
    sorry,
end


theorem or_thm : ∀ x y : bool, is_tt x ∨ is_tt y ↔ is_tt (x || y) :=
begin
    sorry,
end



end bool
