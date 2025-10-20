variables A B: Type
variables PP QQ : A → Prop
variables P R : Prop



example : (∀ x: A, PP x ∧ QQ x)
        ↔ (∀ x: A, PP x) ∧ (∀ x: A, QQ x) :=
begin
    constructor,
    assume h1,
    constructor,
    assume x,
    have h2 : PP x ∧ QQ x,
    apply h1,
    cases h2 with h3 h4,
    exact h3,
    assume x,
    have h5 : PP x ∧ QQ x,
    apply h1,
    cases h5 with h6 h7,
    exact h7,
    assume h8,
    cases h8 with h9 h10,
    assume x,
    constructor,
    apply h9,
    apply h10,
end


example : (∀ x: A, PP x) 
    → (∀ y: A, PP y → QQ y) 
    → ∀ z : A, QQ z :=
begin
    assume h1,
    assume h2,
    assume x,
    apply h2,
    apply h1,
end

example : (∃ x : A, PP x)
        → (∀ y: A, PP y → QQ y)
        → (∃ z: A, QQ z) :=
begin
    assume h1,
    assume h2,
    cases h1 with a ha,
    existsi a,
    apply h2,
    exact ha,
end

example : (∃ x: A, PP x ∨ QQ x)
        ↔ (∃ x: A, PP x) ∨ (∃ x: A, QQ x) :=
begin
    constructor,
    assume h1,
    cases h1 with a ha,
    cases ha with h2 h3,
    left,
    existsi a,
    exact h2,
    right,
    existsi a,
    exact h3,
    assume h4,
    cases h4 with h5 h6,
    cases h5 with b hb,
    existsi b,
    left,
    exact hb,
    cases h6 with c hc,
    existsi c,
    right,
    exact hc,
end

theorem curry_pred: (∃ x: A, PP x) → R
                  ↔ (∀ x: A, PP x → R) :=
begin
     sorry,
end

example : ∀ x : A, x = x :=
begin
    sorry,
end

example : ∀ x y : A, x = y → PP y → PP x :=
begin
    sorry,
end

example : ∀ x y : A, x = y → PP x → PP y :=
begin
    sorry,
end

theorem sym_eq : ∀ x y : A, x = y → y = x :=
begin
    sorry,
end

theorem trans_eq: ∀ x y z : A, x = y → y = z → x = z :=
begin
   sorry,
end


theorem congr_argf: ∀ f : A → B, ∀ x y : A, x = y → f x = f y :=
begin
    sorry,
end


theorem dm1_pred : ¬ (∃ x : A, PP x) ↔ ∀ x : A, ¬ PP x :=
begin
    sorry,
end



theorem dm2_pred : ¬ (∀ x : A, PP x) ↔ ∃ x : A, ¬ PP x :=
begin
    sorry,
end











