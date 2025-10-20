
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
| tt := ff
| ff := tt 

def band : bool → bool → bool
| tt b := b
| ff b := ff

def bor : bool → bool → bool
| tt b := tt
| ff b := b
-/

example : ∀ x : bool, x = tt ∨ x = ff :=
begin
    assume b,
    cases b,
    right,
    refl,
    left,
    refl,
end

def is_tt : bool → Prop
| ff := false
| tt := true

theorem cons : tt ≠ ff :=
begin
    assume h,
    /-change is_tt ff,
    rewrite <- h,
    constructor,-/
    contradiction,
end



theorem distr_b : ∀ x y z : bool, 
    x && (y || z) = x && y || x && z :=
begin
    assume a b c,
    cases a,
    --dsimp [band, bor],
    refl,
    --dsimp [band, bor],
    refl,
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
    assume b,
    constructor,
    assume h,
    cases b,
    constructor,
    have ht : is_tt true,
    constructor,
    contradiction,
    sorry,
end


theorem or_thm : ∀ x y : bool, is_tt x ∨ is_tt y ↔ is_tt (x || y) :=
begin
    sorry,
end



end bool
