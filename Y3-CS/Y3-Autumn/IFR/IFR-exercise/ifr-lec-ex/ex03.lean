/-
COMP2068 IFR 
Exercise 03

Prove all the following propositions in Lean.
This is to replace "sorry" with your proof. 

You are only allowed to use the tactics introduced in the lecture.
You may use laws in classical logic if a statement is not provable in intuitionistic logic. 
-/



namespace proofs


def bnot : bool → bool 
| tt := ff
| ff := tt 

def band : bool → bool → bool 
| tt b := b
| ff b := ff

def bor : bool → bool → bool 
| tt b := tt
| ff b := b

def is_tt : bool → Prop 
| tt := true
| ff := false


local notation (name := band) x && y := band x y 
local notation (name := bor) x || y := bor x y

/-If the above two lines report errors, then use 
local notation  x && y := band x y 
local notation  x || y := bor x y
-/


prefix `!`:90 := bnot

/- --- Do not add/change anything above this line --- -/



theorem q01: ¬ (∀ x : bool, ! x = x) :=
begin
    assume h,
    have h1 : !tt = tt,
    apply h,
    cases h1,
end

theorem q02: ∀ x:bool,∃ y:bool, x = y :=
begin 
    assume h,
    cases h,
    existsi ff,
    refl,
    existsi tt,
    refl,
end


theorem q03: ¬ (∃ x:bool,∀ y:bool, x = y) :=
begin
    assume h,
    cases h with a h1,
    cases a,
    have h2 : ff = tt,
    apply h1,
    cases h2,
    have h3 : tt = ff,
    apply h1,
    cases h3,
end

theorem q04: ∀ x y : bool, x = y → ! x = ! y :=
begin
    assume a b,
    assume h,
    rw h,
end

theorem q05: ∀ x y : bool, !x = !y → x = y :=
begin
    assume a b,
    assume h,
    cases a,
    cases b,
    refl,
    cases h,
    cases b,
    cases h,
    constructor,
end


theorem q06: ¬ (∀ x y z : bool, x=y ∨ y=z)  :=
begin
    assume h,
    have f: tt=ff ∨ ff = tt,
    apply h,
    cases f with f1 f2,
    cases f1,
    cases f2,
end 

theorem q07: ∃ b : bool, ∀ y:bool, b || y = y :=
begin
    have h: ∀ y: bool, ff || y = y,
    assume b,
    refl,
    existsi ff,
    exact h,
end

theorem q08: ∃ b : bool, ∀ y:bool, b || y = b :=
begin
    have h: ∀ y: bool, tt || y = tt,
    assume b,
    refl,
    existsi tt,
    exact h,
end

theorem q09: ∀ x : bool, (∀ y : bool, x && y = y) ↔ x = tt :=
begin
    assume b,
    constructor,
    assume h,
    have f1 : b&&tt = tt,
    apply h,
    cases b,
    cases f1,
    constructor,
    assume h,
    assume b2,
    rw h,
    refl,
end

theorem q10: ¬ (∀ x y : bool, x && y = y ↔ x = ff) :=
begin
    assume h,
    have f : ∀ y : bool, tt && y = y ↔ tt= ff,
    apply h,
    have f1 : tt&&tt = tt ↔ tt = ff,
    apply f,
    cases f1 with f1l f1r,
    have f2 : tt = ff,
    apply f1l,
    constructor,
    cases f2,
end

/- ---Do not add/change anything below this line --- -/
end proofs
