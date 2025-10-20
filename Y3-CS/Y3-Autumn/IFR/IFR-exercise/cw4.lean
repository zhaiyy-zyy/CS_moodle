/-
COMP2068-IFR Coursework 4 (100)
(Functor and Naturality)
In this Coursework, you will need to prove some properties relate
to functor and naturality
-/
set_option pp.structure_projections false
variables {A B C D: Type}

namespace cw4
open list

/- 
In the lecture we have introduced the id and composition function.
Also, we have discussed the mapping on a list
-/

def id : A → A 
| a := a

def comp : (B → C) → (A → B) → A → C
| g f a := g (f a)
local notation (name := comp) f ∘ g := comp f g
/-
If you get an error update your lean or use:
local notation f ∘ g := comp f g
-/

def map_list : (A → B) → list A → list B 
| f [] := []
| f (a :: as) := (f a) :: (map_list f as)

/-
In the lecture we have introduced the definition os rev and snoc
-/

-- cons in another direction
def snoc : list A → A → list A
| []  a := [a]
| (a :: as) b := a :: (snoc as b)

-- Definition of the reverse function
def rev : list A → list A
| [] := []
| (a :: as) := snoc (rev as) a


/- --- Do not add/change anything above this line --- -/


/- a) Prove that id is left netrual (10%)-/
theorem idl : ∀ f : A → B, id ∘ f = f := sorry

/- b) Prove that id is right netrual (10%)-/
theorem idr : ∀ f : A → B, f ∘ id = f := sorry

/- c) Prove that ∘ is associative (10%)-/
theorem assoc : ∀ h : C → D, ∀ g : B → C,
  ∀ f : A → B, (h ∘ g) ∘ f = h ∘ (g ∘ f) := sorry

/- d) Prove that map_list preserves composition (20%)-/
theorem map_comp : ∀ g : B → C, ∀ f : A → B,
  map_list (g ∘ f) = (map_list g) ∘ (map_list f) := 
begin 
  assume g f,
  apply funext,
  assume x,
  induction x with x xs ih,
  refl,
  dsimp[map_list],
  rw ih,
  refl,
end

/- e) Prove that rev is natural (50%), You may need some lemmas -/
lemma lem1 : ∀ f : A → B, ∀ as : list A, ∀ a : A, 
  map_list f (snoc as a) = snoc (map_list f as) (f a) :=
begin
  assume f,
  assume as,
  induction as with x xs ih,
  dsimp[snoc],
  assume a,
  refl,
  assume a,
  dsimp[map_list],
  dsimp[snoc],
  dsimp[map_list],
  rw ih,
end

-- h: map_list f (rev as) = rev (map_list f as)

theorem nat_rev {A B : Type*} : ∀ f : A → B, ∀ l : list A,
  map_list f (rev l) = rev (map_list f l) :=
begin
  assume f,
  assume l,
  induction l with a as ih,
  dsimp[rev],
  dsimp[map_list],
  refl,
  dsimp [rev, map_list],
  rw lem1,
  rw ih,
end

/- --- Do not add/change anything below this line --- -/
end cw4
