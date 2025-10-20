/-
COMP2068 IFR 
Exercise 02

Prove all the following propositions in Lean.
This is to replace "sorry" with your proof. 

You are only allowed to use the tactics introduced in the lecture.
You may use laws in classical logic if a statement is not provable in intuitionistic logic. 
-/



namespace proofs

variables P Q R : Prop

/- ---Do not add/change anything above this line --- -/

theorem q01: P → (P → Q) → P ∧ Q :=
begin
  -- P → (P → Q) → P and P → (P → Q) → Q
  assume p pq,
  constructor,
  exact p,
  apply pq,
  exact p,
end

theorem q02: P ∨ Q → (P → Q) → Q :=
begin 
  -- P ∨ Q → (P → Q) → Q
  -- P → (P → Q) → Q and Q → (P → Q) → Q
  assume pq p2q,
  cases pq with p q,
  apply p2q,
  exact p,
  --
  exact q,
end


open classical
theorem q03: (P → Q)→ ¬ P ∨ Q :=
begin
  -- (P → Q)→ ¬ P ∨ Q
  -- (P → Q)→ ¬ P or (P → Q)→ Q
  assume pq,
  cases (em P) with p np,
  right,
  apply pq,
  exact p,
  left,
  exact np,
end

theorem q04: (¬ P ∨ Q) → P → Q :=
begin
  -- (¬ P ∨ Q) → P → Q
  -- ¬ P → P → Q and Q → P → Q
  assume npq p,
  cases npq with np q,
  have x : false,
  apply np,
  exact p,
  cases x,
  --
  exact q,
end

theorem q05: ¬ P ↔ ¬ ¬ ¬ P :=
begin
  constructor,
  assume np nnp,
  apply nnp,
  exact np,
  --
  assume nnnp p,
  apply nnnp,
  assume np,
  apply np,
  exact p,
end


theorem q06: ((P → Q) → P) → P :=
begin
  assume h,
  cases (em P) with p np,
  exact p,
  apply h,
  assume p,
  have x : false,
  apply np,
  exact p,
  cases x,
end 

lemma raa : ¬¬ Q → Q :=
begin
  assume h,
  cases (em Q) with q nq,
  exact q,
  have x : false,
  apply h,
  exact nq,
  cases x,
end

theorem q07: ¬ ¬ (P ∨ ¬ P) → P ∨ ¬ P :=
-- Q = P ∨ ¬ P
-- ¬¬ Q → Q 
begin
  apply raa,
end

theorem q08: ¬ (P ∨ Q) ↔ ¬ ¬ ¬ (P ∨ Q) :=
begin
  apply q05,
end
/- ---Do not add/change anything below this line --- -/
end proofs
