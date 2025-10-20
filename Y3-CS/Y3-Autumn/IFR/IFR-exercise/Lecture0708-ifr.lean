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
    exact px,
    assume x,
    exact qx,

end


example : (∀ x: A, PP x) 
    → (∀ y: A, PP y → QQ y) 
    → ∀ z : A, QQ z :=
begin
    assume h1,
    assume h2,
    assume a,
    apply h2,
    apply h1,
end

example : (∃ x : A, PP x)
        → (∀ y: A, PP y → QQ y)
        → (∃ z: A, QQ z) :=
begin
    assume h1,
    assume h2,
    cases h1 with a pa,
    existsi a,
    apply h2,
    exact pa, 
end

example : (∃ x: A, PP x ∨ QQ x)
        ↔ (∃ x: A, PP x) ∨ (∃ x: A, QQ x) :=
begin
    constructor,
    assume h1,
    cases h1 with a ha,
    cases ha with ppa qqa,
    left,
    existsi a,
    exact ppa,
    right,
    existsi a,
    exact qqa,
    assume h1,
    cases h1 with hp hq,
    cases hp with a ppa,
    existsi a,
    left,
    exact ppa,
    cases hq with a qqa,
    existsi a,
    right,
    exact qqa,
end

theorem curry_pred: (∃ x: A, PP x) → R
                  ↔ (∀ x: A, PP x → R) :=
begin
     constructor,
     assume h1,
     assume a,
     assume ppa,
     apply h1,
     existsi a,
     exact ppa,
     assume h1,
     assume h2,
     apply h1,
     existsi a,
     exact ppa,
     
end

example : ∀ x : A, x = x :=
begin
    assume a,
    --reflexivity,
    refl,
end

example : ∀ x y : A, x = y → PP y → PP x :=
begin
    assume a b,
    assume ab,
    /-rewrite ab,
    assume h,
    exact h,-/
    --rewrite <- ab,
    assume h,
    rewrite ab, --change a into b,
    exact h,
end

example : ∀ x y : A, x = y → PP x → PP y :=
begin
    assume a b,
    assume ab,
    rewrite <- ab,
    assume h,
    exact h,
end

theorem sym_eq : ∀ x y : A, x = y → y = x :=
begin
    assume a b,
    assume h,
    /-symmetry,
    exact h,-/
    rewrite h,
end

theorem trans_eq: ∀ x y z : A, x = y → y = z → x = z :=
begin
   assume a b c,
   assume ab bc,
   /-rewrite ab,
   exact bc,-/
   /-rewrite bc at ab,
   exact ab,-/
   /-transitivity,
   exact ab,
   exact bc,-/
   exact trans ab bc,
end


theorem congr_argf: ∀ f : A → B, ∀ x y : A, x = y → f x = f y :=
begin
    assume f a b,
    assume h,
    rewrite h,
end


theorem dm1_pred : ¬ (∃ x : A, PP x) ↔ ∀ x : A, ¬ PP x :=
begin
    sorry,
end

theorem raa : ¬ ¬ P → P := -- 为theorem dm2_pred引入raa
begin
    assume nnp,
    cases em P with p np,
    exact p,
    have f: false,
    apply nnp,
    exact np,
    cases f,
end

theorem dm2_pred : ¬ (∀ x : A, PP x) ↔ ∃ x : A, ¬ PP x :=
begin
    constructor,
    assume h,
    apply raa,
    assume h1,
    apply h,
    assume a,
    apply raa,
    assume h2,
    apply h1,
    existsi a,
    exact h2,
    sorry,
end











