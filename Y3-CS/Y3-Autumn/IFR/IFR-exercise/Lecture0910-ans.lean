
--set_option pp.structure_projections false

namespace bool

/-
inductive bool : Type
| ff : bool
| tt : bool
-/


--#reduce ff && (tt || ff)
--#reduce tt && (tt || ff)


def bnot : bool → bool 
| tt := ff
| ff := tt

def band : bool → bool → bool
| tt b := b
| ff b := ff

def bor : bool → bool → bool
| tt b := tt
| ff b := b

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
    /-
    assume h,
    cases h,
    -/

    /-
    assume h,
    change is_tt ff,
    rewrite <- h,
    trivial,
    -/

    assume h,
    contradiction,
end



theorem distr_b : ∀ x y z : bool, 
    x && (y || z) = x && y || x && z :=
begin
    /-
    assume a b c,
    cases a,
    dsimp [band],
    refl,
    dsimp [band],
    refl,
    -/
    assume a b c,
    cases a,
    refl,
    refl,
end


theorem dm1_b : ∀ x y : bool, bnot(x || y) = bnot x && bnot y :=
begin
    assume a b,
    cases a,
    refl,
    refl,
end


theorem dm2_b : ∀ x y : bool, bnot(x && y) = bnot x || bnot y :=
begin
    assume a b,
    cases a,
    refl,
    refl,
end




theorem and_thm : ∀ x y : bool, is_tt x ∧ is_tt y ↔ is_tt (x && y) :=
begin
    assume a b,
    constructor,
    assume h1,
    cases h1 with ha hb,
    cases a,
    exact ha,
    exact hb,
    assume h1,
    cases a,
    cases h1,
    constructor,
    constructor,
    exact h1,
end

theorem not_thm : ∀ x : bool, ¬ (is_tt x) ↔ is_tt(bnot x) :=
begin
    assume b,
    constructor,
    assume h,
    cases b,
    dsimp [bnot],
    constructor,
    have t: is_tt tt,
    constructor,
    contradiction,
    assume h,
    assume h1,
    cases b,
    cases h1,
    cases h,
end


theorem or_thm : ∀ x y : bool, is_tt x ∨ is_tt y ↔ is_tt (x || y) :=
begin
    assume a b,
    constructor,
    assume h,
    cases a,
    cases h with hl hr,
    cases hl,
    exact hr,
    cases h with hl hr,
    exact hl,
    constructor,
    assume h,
    cases a,
    right,
    exact h,
    left,
    constructor,
end

example : ∀ x : bool, ∃ y : bool, x ≠ y :=
begin
  assume x,
  cases x,
  
end

end bool
