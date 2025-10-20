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
    have f : !tt = tt,
    apply h,
    cases f,
end

theorem q02: ∀ x:bool,∃ y:bool, x = y :=
begin 
    assume a,
    existsi a,
    constructor,
end


theorem q03: ¬ (∃ x:bool,∀ y:bool, x = y) :=
begin
    assume h,
    cases h with x p,
    cases x,
    have f : ff=tt,
    apply p,
    cases f,
    have f : tt=ff,
    apply p,
    cases f,
end

theorem q04: ∀ x y : bool, x = y → ! x = ! y :=
begin
    assume x y,
    assume eq,
    cases x,
    cases y,
    constructor,
    cases eq,
    cases y,
    cases eq,
    constructor,
end

theorem q05: ∀ x y : bool, !x = !y → x = y :=
begin
    assume x y,
    assume eq,
    cases x,
    cases y,
    constructor,
    cases eq,
    cases y,
    cases eq,
    constructor,
end


theorem q06: ¬ (∀ x y z : bool, x=y ∨ y=z)  :=
begin
    assume h,
    have f : tt = ff ∨ ff = tt,
    apply h,
    cases f with a b,
    cases a,
    cases b,
end 

theorem q07: ∃ b : bool, ∀ y:bool, b || y = y :=
begin
    have f : ff || tt = tt,
    refl,
    existsi ff,
    assume y,
    cases y,
    refl,
    refl,
end

theorem q08: ∃ b : bool, ∀ y:bool, b || y = b :=
begin
    have f : tt || ff = tt,
    refl,
    existsi tt,
    assume y,
    cases y,
    refl,
    refl,
end

theorem q09: ∀ x : bool, (∀ y : bool, x && y = y) ↔ x = tt :=
begin
    assume x,
    constructor,
    assume h,
    cases x,
    have f : ff && tt = tt,
    apply h,
    cases f,
    refl,
    assume eq,
    cases x,
    cases eq,
    assume y,
    cases y,
    refl,
    refl,
end

theorem q10: ¬ (∀ x y : bool, x && y = y ↔ x = ff) :=
begin
    assume h,
    have f : tt&&ff = ff ↔ tt = ff,
    apply h,
    cases f with l r,
    have ff : tt = ff,
    apply l,
    refl,
    cases ff,
end

/- ---Do not add/change anything below this line --- -/
end proofs
