variables P Q : Prop

open classical

--#check em P

theorem dm1 : ¬ (P ∨ Q) ↔ ¬ P ∧ ¬ Q :=
begin
  constructor,
  assume npq,
  constructor,
  assume p,
  apply npq,
  left,
  exact p,
  assume q,
  apply npq,
  right,
  exact q,
  assume npnq, 
  assume pq,
  cases npnq with np nq,
  cases pq with p q,
  apply np,
  exact p,
  apply nq,
  exact q,
end

theorem dm2_em : ¬ (P ∧ Q) → ¬ P ∨ ¬ Q :=
begin
  assume npq,
  cases (em P) with p np,
  right,
  assume q,
  apply npq,
  constructor,
  exact p,
  exact q,
  left,
  exact np,
end

theorem dm2 : ¬ (P ∧ Q) ↔ ¬ P ∨ ¬ Q :=
begin
    constructor,
    apply dm2_em,
    assume npnq,
    assume pq,
    cases pq with p q,
    cases npnq with np nq,
    apply np,
    exact p,
    apply nq,
    exact q,
end

theorem raa : ¬ ¬ P → P :=
begin
    assume nnp,
    cases em P with p np,
    exact p,
    have f: false,
    apply nnp,
    exact np,
    cases f,
end
example (a b : ℕ) (h : a = b) : a = b :=
begin
  exact h, -- 直接使用假设 h 证明目标
end
example (a : ℕ) : a = a :=
begin
  refl, -- Reflexivity of equality
end

example (a b : ℕ) (h : a = b) : b = a :=
begin
  rw h, -- 用假设 h 将 a 替换为 b
end

example (a b c : ℕ) (h1 : a = b) (h2 : b = c) : a = c :=
begin
  rw h1,   -- Rewrite a with b using h1
  rw h2,   -- Rewrite b with c using h2
end

theorem raa1 : ¬ ¬ ¬ P → ¬ P :=
begin
    assume nnnp,
    assume p,
    apply nnnp,
    assume np,
    have f : false,
    apply np,
    exact p,
    cases f,
end

theorem nn_em : ¬ ¬ (P ∨ ¬ P) :=
begin
    assume npnp,
    apply npnp,
    right,
    assume p,
    apply npnp,
    left,
    exact p,
end

constant raa_c : ¬ ¬ P → P

theorem em : P ∨ ¬ P :=
begin
    apply raa_c,
    apply nn_em,
end



