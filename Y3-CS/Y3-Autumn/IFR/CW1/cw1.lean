/-
COMP2068-IFR 
Coursework 01 (10 points)

Prove all the following propositions in Lean. 1 point per exercise.
That is replace "sorry" with your proof. 

You are only allowed to use the tactics introduced in the lecture
(i.e. assume, exact, apply, constructor, cases, left, right)

-/
namespace proofs

variables P Q R : Prop

/- --- Do not add/change anything above this line --- -/


theorem q01 : P → Q → P :=
begin
  assume p,
  assume q,
  exact p,
end

theorem q02 : (P → Q → R) → (P → Q) → P → R :=
begin
  assume pqr,
  assume pq,
  assume p,
  apply pqr,
  exact p,
  apply pq,
  exact p,
end

theorem q03 : (P → Q) → P ∧ R → Q ∧ R :=
begin
  assume p2q,
  assume pandr,
  cases pandr with p r,
  constructor,
  apply p2q,
  exact p,
  exact r,
end

theorem q04 : (P → Q) → P ∨ R → Q ∨ R :=
begin
  assume p2q,
  assume porr,
  cases porr with p r,
  left,
  apply p2q,
  exact p,
  right,
  exact r,
end

theorem q05 : P ∨ Q → R ↔ (P → R) ∧ (Q → R) :=
begin
  constructor,
  assume porq2r,
  constructor,
  assume p,
  apply porq2r,
  left,
  exact p,
  assume q,
  apply porq2r,
  right,
  exact q,
  assume pandq2r,
  cases pandq2r with p2r q2r,
  assume porq,
  cases porq with p q,
  apply p2r,
  exact p,
  apply q2r,
  exact q,

end

theorem q06 : P → ¬ ¬ P :=
begin
  assume p,
  assume np,
  exact np p,

end

theorem q07 : P ∧ true ↔ P :=
begin
  constructor,
  assume pandtrue,
  cases pandtrue with p t,
  exact p,
  assume p,
  constructor,
  exact p,
  exact true.intro,
end

theorem q08 : P ∨ false ↔ P :=
begin
constructor,
  assume porfalse,
  cases porfalse with p f,
  exact p,
  cases f,
  assume p,
  left,
  exact p,
end

theorem q09 : P ∧ false ↔ false :=
begin
  constructor,
  assume pandfalse,
  cases pandfalse with p f,
  exact f,
  assume f,
  cases f,
end

theorem q10 : P ∨ true ↔ true :=
begin
  constructor,
  assume portrue,
  cases portrue with p t,
  exact true.intro,
  exact t,
  assume t,
  right,
  exact true.intro,

end

/- --- Do not add/change anything below this line --- -/
end proofs