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
    have h' : !tt = tt,
    apply h,
    dsimp[bnot] at h',
    cases h',
    --contradiction,
end

theorem q02: ∀ x:bool,∃ y:bool, x = y :=
begin 
    assume x,
    existsi x,
    refl,
end


theorem q03: ¬ (∃ x:bool,∀ y:bool, x = y) :=
begin
    assume h,
    cases h with k h,
    have h' : k = !k,
    apply h,
    cases k,
    dsimp[bnot] at h',
    cases h',
    dsimp[bnot] at h',
    cases h',
end

theorem q04: ∀ x y : bool, x = y → ! x = ! y :=
begin
    assume x y xy,
    rewrite xy,
end

theorem q05: ∀ x y : bool, !x = !y → x = y :=
begin
    assume x y nxy,
    cases x,
    cases y,
    refl,
    cases nxy,
    cases y,
    cases nxy,
    refl,
end


theorem q06: ¬ (∀ x y z : bool, x=y ∨ y=z)  :=
begin
    assume h,
    have h' : tt = ff ∨ ff = tt,
    apply h,
    cases h' with h1 h2,
    cases h1,
    cases h2,
end 

theorem q07: ∃ b : bool, ∀ y:bool, b || y = y :=
begin
    existsi ff,
    assume y,
    --dsimp[bor],
    refl,
end

theorem q08: ∃ b : bool, ∀ y:bool, b || y = b :=
begin
    existsi tt,
    assume y,
    --dsimp[bor],
    refl,
end

theorem q09: ∀ x : bool, (∀ y : bool, x && y = y) ↔ x = tt :=
begin
    assume x,
    --
    constructor,
    assume h,
    cases x,
    have h' : ff && tt = tt,
    apply h,
    cases h',
    refl,
    --
    assume h,
    rewrite h,
    dsimp[band],
    assume y,
    refl,
end

theorem q10: ¬ (∀ x y : bool, x && y = y ↔ x = ff) :=
begin
    assume h,
    -- x = tt, y = tt
    have h' : tt && tt = tt ↔ tt = ff,
    apply h,
    dsimp [band] at h',
    cases h' with h1 h2,
    have h'' : tt = ff,
    apply h1,
    refl,
    cases h'',
end

/- ---Do not add/change anything below this line --- -/
end proofs
