.class public Landroid/support/constraint/solver/widgets/ConstraintWidgetContainer;
.super Landroid/support/constraint/solver/widgets/WidgetContainer;
.source "ConstraintWidgetContainer.java"


# static fields
.field static ALLOW_ROOT_GROUP:Z = false

.field private static final DEBUG:Z = false

.field private static final USE_DIRECT_CHAIN_RESOLUTION:Z = true

.field private static final USE_SNAPSHOT:Z = true

.field private static final USE_THREAD:Z


# instance fields
.field protected mBackgroundSystem:Landroid/support/constraint/solver/LinearSystem;

.field private mHorizontalChainsArray:[Landroid/support/constraint/solver/widgets/ConstraintWidget;

.field private mHorizontalChainsSize:I

.field private mMatchConstraintsChainedWidgets:[Landroid/support/constraint/solver/widgets/ConstraintWidget;

.field mPaddingBottom:I

.field mPaddingLeft:I

.field mPaddingRight:I

.field mPaddingTop:I

.field private mSnapshot:Landroid/support/constraint/solver/widgets/Snapshot;

.field protected mSystem:Landroid/support/constraint/solver/LinearSystem;

.field private mVerticalChainsArray:[Landroid/support/constraint/solver/widgets/ConstraintWidget;

.field private mVerticalChainsSize:I

.field mWrapHeight:I

.field mWrapWidth:I


# direct methods
.method static constructor <clinit>()V
    .locals 1

    .prologue
    .line 41
    const/4 v0, 0x1

    sput-boolean v0, Landroid/support/constraint/solver/widgets/ConstraintWidgetContainer;->ALLOW_ROOT_GROUP:Z

    return-void
.end method

.method public constructor <init>()V
    .locals 3

    .prologue
    const/4 v2, 0x0

    const/4 v1, 0x4

    .line 64
    invoke-direct {p0}, Landroid/support/constraint/solver/widgets/WidgetContainer;-><init>()V

    .line 36
    new-instance v0, Landroid/support/constraint/solver/LinearSystem;

    invoke-direct {v0}, Landroid/support/constraint/solver/LinearSystem;-><init>()V

    iput-object v0, p0, Landroid/support/constraint/solver/widgets/ConstraintWidgetContainer;->mSystem:Landroid/support/constraint/solver/LinearSystem;

    .line 37
    const/4 v0, 0x0

    iput-object v0, p0, Landroid/support/constraint/solver/widgets/ConstraintWidgetContainer;->mBackgroundSystem:Landroid/support/constraint/solver/LinearSystem;

    .line 51
    iput v2, p0, Landroid/support/constraint/solver/widgets/ConstraintWidgetContainer;->mHorizontalChainsSize:I

    .line 52
    iput v2, p0, Landroid/support/constraint/solver/widgets/ConstraintWidgetContainer;->mVerticalChainsSize:I

    .line 53
    new-array v0, v1, [Landroid/support/constraint/solver/widgets/ConstraintWidget;

    iput-object v0, p0, Landroid/support/constraint/solver/widgets/ConstraintWidgetContainer;->mMatchConstraintsChainedWidgets:[Landroid/support/constraint/solver/widgets/ConstraintWidget;

    .line 54
    new-array v0, v1, [Landroid/support/constraint/solver/widgets/ConstraintWidget;

    iput-object v0, p0, Landroid/support/constraint/solver/widgets/ConstraintWidgetContainer;->mVerticalChainsArray:[Landroid/support/constraint/solver/widgets/ConstraintWidget;

    .line 55
    new-array v0, v1, [Landroid/support/constraint/solver/widgets/ConstraintWidget;

    iput-object v0, p0, Landroid/support/constraint/solver/widgets/ConstraintWidgetContainer;->mHorizontalChainsArray:[Landroid/support/constraint/solver/widgets/ConstraintWidget;

    .line 65
    return-void
.end method

.method public constructor <init>(II)V
    .locals 3
    .param p1, "width"    # I
    .param p2, "height"    # I

    .prologue
    const/4 v2, 0x0

    const/4 v1, 0x4

    .line 86
    invoke-direct {p0, p1, p2}, Landroid/support/constraint/solver/widgets/WidgetContainer;-><init>(II)V

    .line 36
    new-instance v0, Landroid/support/constraint/solver/LinearSystem;

    invoke-direct {v0}, Landroid/support/constraint/solver/LinearSystem;-><init>()V

    iput-object v0, p0, Landroid/support/constraint/solver/widgets/ConstraintWidgetContainer;->mSystem:Landroid/support/constraint/solver/LinearSystem;

    .line 37
    const/4 v0, 0x0

    iput-object v0, p0, Landroid/support/constraint/solver/widgets/ConstraintWidgetContainer;->mBackgroundSystem:Landroid/support/constraint/solver/LinearSystem;

    .line 51
    iput v2, p0, Landroid/support/constraint/solver/widgets/ConstraintWidgetContainer;->mHorizontalChainsSize:I

    .line 52
    iput v2, p0, Landroid/support/constraint/solver/widgets/ConstraintWidgetContainer;->mVerticalChainsSize:I

    .line 53
    new-array v0, v1, [Landroid/support/constraint/solver/widgets/ConstraintWidget;

    iput-object v0, p0, Landroid/support/constraint/solver/widgets/ConstraintWidgetContainer;->mMatchConstraintsChainedWidgets:[Landroid/support/constraint/solver/widgets/ConstraintWidget;

    .line 54
    new-array v0, v1, [Landroid/support/constraint/solver/widgets/ConstraintWidget;

    iput-object v0, p0, Landroid/support/constraint/solver/widgets/ConstraintWidgetContainer;->mVerticalChainsArray:[Landroid/support/constraint/solver/widgets/ConstraintWidget;

    .line 55
    new-array v0, v1, [Landroid/support/constraint/solver/widgets/ConstraintWidget;

    iput-object v0, p0, Landroid/support/constraint/solver/widgets/ConstraintWidgetContainer;->mHorizontalChainsArray:[Landroid/support/constraint/solver/widgets/ConstraintWidget;

    .line 87
    return-void
.end method

.method public constructor <init>(IIII)V
    .locals 3
    .param p1, "x"    # I
    .param p2, "y"    # I
    .param p3, "width"    # I
    .param p4, "height"    # I

    .prologue
    const/4 v2, 0x0

    const/4 v1, 0x4

    .line 76
    invoke-direct {p0, p1, p2, p3, p4}, Landroid/support/constraint/solver/widgets/WidgetContainer;-><init>(IIII)V

    .line 36
    new-instance v0, Landroid/support/constraint/solver/LinearSystem;

    invoke-direct {v0}, Landroid/support/constraint/solver/LinearSystem;-><init>()V

    iput-object v0, p0, Landroid/support/constraint/solver/widgets/ConstraintWidgetContainer;->mSystem:Landroid/support/constraint/solver/LinearSystem;

    .line 37
    const/4 v0, 0x0

    iput-object v0, p0, Landroid/support/constraint/solver/widgets/ConstraintWidgetContainer;->mBackgroundSystem:Landroid/support/constraint/solver/LinearSystem;

    .line 51
    iput v2, p0, Landroid/support/constraint/solver/widgets/ConstraintWidgetContainer;->mHorizontalChainsSize:I

    .line 52
    iput v2, p0, Landroid/support/constraint/solver/widgets/ConstraintWidgetContainer;->mVerticalChainsSize:I

    .line 53
    new-array v0, v1, [Landroid/support/constraint/solver/widgets/ConstraintWidget;

    iput-object v0, p0, Landroid/support/constraint/solver/widgets/ConstraintWidgetContainer;->mMatchConstraintsChainedWidgets:[Landroid/support/constraint/solver/widgets/ConstraintWidget;

    .line 54
    new-array v0, v1, [Landroid/support/constraint/solver/widgets/ConstraintWidget;

    iput-object v0, p0, Landroid/support/constraint/solver/widgets/ConstraintWidgetContainer;->mVerticalChainsArray:[Landroid/support/constraint/solver/widgets/ConstraintWidget;

    .line 55
    new-array v0, v1, [Landroid/support/constraint/solver/widgets/ConstraintWidget;

    iput-object v0, p0, Landroid/support/constraint/solver/widgets/ConstraintWidgetContainer;->mHorizontalChainsArray:[Landroid/support/constraint/solver/widgets/ConstraintWidget;

    .line 77
    return-void
.end method

.method private addHorizontalChain(Landroid/support/constraint/solver/widgets/ConstraintWidget;)V
    .locals 3
    .param p1, "widget"    # Landroid/support/constraint/solver/widgets/ConstraintWidget;

    .prologue
    .line 1358
    const/4 v0, 0x0

    .local v0, "i":I
    :goto_0
    iget v1, p0, Landroid/support/constraint/solver/widgets/ConstraintWidgetContainer;->mHorizontalChainsSize:I

    if-ge v0, v1, :cond_1

    .line 1359
    iget-object v1, p0, Landroid/support/constraint/solver/widgets/ConstraintWidgetContainer;->mHorizontalChainsArray:[Landroid/support/constraint/solver/widgets/ConstraintWidget;

    aget-object v1, v1, v0

    if-ne v1, p1, :cond_0

    .line 1368
    :goto_1
    return-void

    .line 1358
    :cond_0
    add-int/lit8 v0, v0, 0x1

    goto :goto_0

    .line 1363
    :cond_1
    iget v1, p0, Landroid/support/constraint/solver/widgets/ConstraintWidgetContainer;->mHorizontalChainsSize:I

    add-int/lit8 v1, v1, 0x1

    iget-object v2, p0, Landroid/support/constraint/solver/widgets/ConstraintWidgetContainer;->mHorizontalChainsArray:[Landroid/support/constraint/solver/widgets/ConstraintWidget;

    array-length v2, v2

    if-lt v1, v2, :cond_2

    .line 1364
    iget-object v1, p0, Landroid/support/constraint/solver/widgets/ConstraintWidgetContainer;->mHorizontalChainsArray:[Landroid/support/constraint/solver/widgets/ConstraintWidget;

    iget-object v2, p0, Landroid/support/constraint/solver/widgets/ConstraintWidgetContainer;->mHorizontalChainsArray:[Landroid/support/constraint/solver/widgets/ConstraintWidget;

    array-length v2, v2

    mul-int/lit8 v2, v2, 0x2

    invoke-static {v1, v2}, Ljava/util/Arrays;->copyOf([Ljava/lang/Object;I)[Ljava/lang/Object;

    move-result-object v1

    check-cast v1, [Landroid/support/constraint/solver/widgets/ConstraintWidget;

    iput-object v1, p0, Landroid/support/constraint/solver/widgets/ConstraintWidgetContainer;->mHorizontalChainsArray:[Landroid/support/constraint/solver/widgets/ConstraintWidget;

    .line 1366
    :cond_2
    iget-object v1, p0, Landroid/support/constraint/solver/widgets/ConstraintWidgetContainer;->mHorizontalChainsArray:[Landroid/support/constraint/solver/widgets/ConstraintWidget;

    iget v2, p0, Landroid/support/constraint/solver/widgets/ConstraintWidgetContainer;->mHorizontalChainsSize:I

    aput-object p1, v1, v2

    .line 1367
    iget v1, p0, Landroid/support/constraint/solver/widgets/ConstraintWidgetContainer;->mHorizontalChainsSize:I

    add-int/lit8 v1, v1, 0x1

    iput v1, p0, Landroid/support/constraint/solver/widgets/ConstraintWidgetContainer;->mHorizontalChainsSize:I

    goto :goto_1
.end method

.method private addVerticalChain(Landroid/support/constraint/solver/widgets/ConstraintWidget;)V
    .locals 3
    .param p1, "widget"    # Landroid/support/constraint/solver/widgets/ConstraintWidget;

    .prologue
    .line 1377
    const/4 v0, 0x0

    .local v0, "i":I
    :goto_0
    iget v1, p0, Landroid/support/constraint/solver/widgets/ConstraintWidgetContainer;->mVerticalChainsSize:I

    if-ge v0, v1, :cond_1

    .line 1378
    iget-object v1, p0, Landroid/support/constraint/solver/widgets/ConstraintWidgetContainer;->mVerticalChainsArray:[Landroid/support/constraint/solver/widgets/ConstraintWidget;

    aget-object v1, v1, v0

    if-ne v1, p1, :cond_0

    .line 1387
    :goto_1
    return-void

    .line 1377
    :cond_0
    add-int/lit8 v0, v0, 0x1

    goto :goto_0

    .line 1382
    :cond_1
    iget v1, p0, Landroid/support/constraint/solver/widgets/ConstraintWidgetContainer;->mVerticalChainsSize:I

    add-int/lit8 v1, v1, 0x1

    iget-object v2, p0, Landroid/support/constraint/solver/widgets/ConstraintWidgetContainer;->mVerticalChainsArray:[Landroid/support/constraint/solver/widgets/ConstraintWidget;

    array-length v2, v2

    if-lt v1, v2, :cond_2

    .line 1383
    iget-object v1, p0, Landroid/support/constraint/solver/widgets/ConstraintWidgetContainer;->mVerticalChainsArray:[Landroid/support/constraint/solver/widgets/ConstraintWidget;

    iget-object v2, p0, Landroid/support/constraint/solver/widgets/ConstraintWidgetContainer;->mVerticalChainsArray:[Landroid/support/constraint/solver/widgets/ConstraintWidget;

    array-length v2, v2

    mul-int/lit8 v2, v2, 0x2

    invoke-static {v1, v2}, Ljava/util/Arrays;->copyOf([Ljava/lang/Object;I)[Ljava/lang/Object;

    move-result-object v1

    check-cast v1, [Landroid/support/constraint/solver/widgets/ConstraintWidget;

    iput-object v1, p0, Landroid/support/constraint/solver/widgets/ConstraintWidgetContainer;->mVerticalChainsArray:[Landroid/support/constraint/solver/widgets/ConstraintWidget;

    .line 1385
    :cond_2
    iget-object v1, p0, Landroid/support/constraint/solver/widgets/ConstraintWidgetContainer;->mVerticalChainsArray:[Landroid/support/constraint/solver/widgets/ConstraintWidget;

    iget v2, p0, Landroid/support/constraint/solver/widgets/ConstraintWidgetContainer;->mVerticalChainsSize:I

    aput-object p1, v1, v2

    .line 1386
    iget v1, p0, Landroid/support/constraint/solver/widgets/ConstraintWidgetContainer;->mVerticalChainsSize:I

    add-int/lit8 v1, v1, 0x1

    iput v1, p0, Landroid/support/constraint/solver/widgets/ConstraintWidgetContainer;->mVerticalChainsSize:I

    goto :goto_1
.end method

.method private applyDirectResolutionHorizontalChain(Landroid/support/constraint/solver/LinearSystem;ILandroid/support/constraint/solver/widgets/ConstraintWidget;)V
    .locals 19
    .param p1, "system"    # Landroid/support/constraint/solver/LinearSystem;
    .param p2, "numMatchConstraints"    # I
    .param p3, "widget"    # Landroid/support/constraint/solver/widgets/ConstraintWidget;

    .prologue
    .line 349
    move-object/from16 v7, p3

    .line 351
    .local v7, "firstWidget":Landroid/support/constraint/solver/widgets/ConstraintWidget;
    const/16 v16, 0x0

    .line 352
    .local v16, "widgetSize":I
    const/4 v6, 0x0

    .line 353
    .local v6, "firstPosition":I
    const/4 v10, 0x0

    .line 354
    .local v10, "previous":Landroid/support/constraint/solver/widgets/ConstraintWidget;
    const/4 v3, 0x0

    .line 355
    .local v3, "count":I
    const/4 v15, 0x0

    .line 358
    .local v15, "totalWeights":F
    :cond_0
    :goto_0
    if-eqz p3, :cond_6

    .line 359
    add-int/lit8 v3, v3, 0x1

    .line 360
    move-object/from16 v0, p3

    iget-object v0, v0, Landroid/support/constraint/solver/widgets/ConstraintWidget;->mHorizontalDimensionBehaviour:Landroid/support/constraint/solver/widgets/ConstraintWidget$DimensionBehaviour;

    move-object/from16 v17, v0

    sget-object v18, Landroid/support/constraint/solver/widgets/ConstraintWidget$DimensionBehaviour;->MATCH_CONSTRAINT:Landroid/support/constraint/solver/widgets/ConstraintWidget$DimensionBehaviour;

    move-object/from16 v0, v17

    move-object/from16 v1, v18

    if-eq v0, v1, :cond_4

    .line 361
    invoke-virtual/range {p3 .. p3}, Landroid/support/constraint/solver/widgets/ConstraintWidget;->getWidth()I

    move-result v17

    add-int v16, v16, v17

    .line 362
    move-object/from16 v0, p3

    iget-object v0, v0, Landroid/support/constraint/solver/widgets/ConstraintWidget;->mLeft:Landroid/support/constraint/solver/widgets/ConstraintAnchor;

    move-object/from16 v17, v0

    move-object/from16 v0, v17

    iget-object v0, v0, Landroid/support/constraint/solver/widgets/ConstraintAnchor;->mTarget:Landroid/support/constraint/solver/widgets/ConstraintAnchor;

    move-object/from16 v17, v0

    if-eqz v17, :cond_2

    move-object/from16 v0, p3

    iget-object v0, v0, Landroid/support/constraint/solver/widgets/ConstraintWidget;->mLeft:Landroid/support/constraint/solver/widgets/ConstraintAnchor;

    move-object/from16 v17, v0

    invoke-virtual/range {v17 .. v17}, Landroid/support/constraint/solver/widgets/ConstraintAnchor;->getMargin()I

    move-result v17

    :goto_1
    add-int v16, v16, v17

    .line 363
    move-object/from16 v0, p3

    iget-object v0, v0, Landroid/support/constraint/solver/widgets/ConstraintWidget;->mRight:Landroid/support/constraint/solver/widgets/ConstraintAnchor;

    move-object/from16 v17, v0

    move-object/from16 v0, v17

    iget-object v0, v0, Landroid/support/constraint/solver/widgets/ConstraintAnchor;->mTarget:Landroid/support/constraint/solver/widgets/ConstraintAnchor;

    move-object/from16 v17, v0

    if-eqz v17, :cond_3

    move-object/from16 v0, p3

    iget-object v0, v0, Landroid/support/constraint/solver/widgets/ConstraintWidget;->mRight:Landroid/support/constraint/solver/widgets/ConstraintAnchor;

    move-object/from16 v17, v0

    invoke-virtual/range {v17 .. v17}, Landroid/support/constraint/solver/widgets/ConstraintAnchor;->getMargin()I

    move-result v17

    :goto_2
    add-int v16, v16, v17

    .line 367
    :goto_3
    move-object/from16 v10, p3

    .line 368
    move-object/from16 v0, p3

    iget-object v0, v0, Landroid/support/constraint/solver/widgets/ConstraintWidget;->mRight:Landroid/support/constraint/solver/widgets/ConstraintAnchor;

    move-object/from16 v17, v0

    move-object/from16 v0, v17

    iget-object v0, v0, Landroid/support/constraint/solver/widgets/ConstraintAnchor;->mTarget:Landroid/support/constraint/solver/widgets/ConstraintAnchor;

    move-object/from16 v17, v0

    if-eqz v17, :cond_5

    move-object/from16 v0, p3

    iget-object v0, v0, Landroid/support/constraint/solver/widgets/ConstraintWidget;->mRight:Landroid/support/constraint/solver/widgets/ConstraintAnchor;

    move-object/from16 v17, v0

    move-object/from16 v0, v17

    iget-object v0, v0, Landroid/support/constraint/solver/widgets/ConstraintAnchor;->mTarget:Landroid/support/constraint/solver/widgets/ConstraintAnchor;

    move-object/from16 v17, v0

    move-object/from16 v0, v17

    iget-object v0, v0, Landroid/support/constraint/solver/widgets/ConstraintAnchor;->mOwner:Landroid/support/constraint/solver/widgets/ConstraintWidget;

    move-object/from16 p3, v0

    .line 369
    :goto_4
    if-eqz p3, :cond_0

    move-object/from16 v0, p3

    iget-object v0, v0, Landroid/support/constraint/solver/widgets/ConstraintWidget;->mLeft:Landroid/support/constraint/solver/widgets/ConstraintAnchor;

    move-object/from16 v17, v0

    move-object/from16 v0, v17

    iget-object v0, v0, Landroid/support/constraint/solver/widgets/ConstraintAnchor;->mTarget:Landroid/support/constraint/solver/widgets/ConstraintAnchor;

    move-object/from16 v17, v0

    if-eqz v17, :cond_1

    move-object/from16 v0, p3

    iget-object v0, v0, Landroid/support/constraint/solver/widgets/ConstraintWidget;->mLeft:Landroid/support/constraint/solver/widgets/ConstraintAnchor;

    move-object/from16 v17, v0

    move-object/from16 v0, v17

    iget-object v0, v0, Landroid/support/constraint/solver/widgets/ConstraintAnchor;->mTarget:Landroid/support/constraint/solver/widgets/ConstraintAnchor;

    move-object/from16 v17, v0

    if-eqz v17, :cond_0

    move-object/from16 v0, p3

    iget-object v0, v0, Landroid/support/constraint/solver/widgets/ConstraintWidget;->mLeft:Landroid/support/constraint/solver/widgets/ConstraintAnchor;

    move-object/from16 v17, v0

    move-object/from16 v0, v17

    iget-object v0, v0, Landroid/support/constraint/solver/widgets/ConstraintAnchor;->mTarget:Landroid/support/constraint/solver/widgets/ConstraintAnchor;

    move-object/from16 v17, v0

    move-object/from16 v0, v17

    iget-object v0, v0, Landroid/support/constraint/solver/widgets/ConstraintAnchor;->mOwner:Landroid/support/constraint/solver/widgets/ConstraintWidget;

    move-object/from16 v17, v0

    move-object/from16 v0, v17

    if-eq v0, v10, :cond_0

    .line 372
    :cond_1
    const/16 p3, 0x0

    goto/16 :goto_0

    .line 362
    :cond_2
    const/16 v17, 0x0

    goto :goto_1

    .line 363
    :cond_3
    const/16 v17, 0x0

    goto :goto_2

    .line 365
    :cond_4
    move-object/from16 v0, p3

    iget v0, v0, Landroid/support/constraint/solver/widgets/ConstraintWidget;->mHorizontalWeight:F

    move/from16 v17, v0

    add-float v15, v15, v17

    goto :goto_3

    .line 368
    :cond_5
    const/16 p3, 0x0

    goto :goto_4

    .line 377
    :cond_6
    const/4 v8, 0x0

    .line 378
    .local v8, "lastPosition":I
    if-eqz v10, :cond_7

    .line 379
    iget-object v0, v10, Landroid/support/constraint/solver/widgets/ConstraintWidget;->mRight:Landroid/support/constraint/solver/widgets/ConstraintAnchor;

    move-object/from16 v17, v0

    move-object/from16 v0, v17

    iget-object v0, v0, Landroid/support/constraint/solver/widgets/ConstraintAnchor;->mTarget:Landroid/support/constraint/solver/widgets/ConstraintAnchor;

    move-object/from16 v17, v0

    if-eqz v17, :cond_b

    iget-object v0, v10, Landroid/support/constraint/solver/widgets/ConstraintWidget;->mRight:Landroid/support/constraint/solver/widgets/ConstraintAnchor;

    move-object/from16 v17, v0

    move-object/from16 v0, v17

    iget-object v0, v0, Landroid/support/constraint/solver/widgets/ConstraintAnchor;->mTarget:Landroid/support/constraint/solver/widgets/ConstraintAnchor;

    move-object/from16 v17, v0

    move-object/from16 v0, v17

    iget-object v0, v0, Landroid/support/constraint/solver/widgets/ConstraintAnchor;->mOwner:Landroid/support/constraint/solver/widgets/ConstraintWidget;

    move-object/from16 v17, v0

    invoke-virtual/range {v17 .. v17}, Landroid/support/constraint/solver/widgets/ConstraintWidget;->getX()I

    move-result v8

    .line 380
    :goto_5
    iget-object v0, v10, Landroid/support/constraint/solver/widgets/ConstraintWidget;->mRight:Landroid/support/constraint/solver/widgets/ConstraintAnchor;

    move-object/from16 v17, v0

    move-object/from16 v0, v17

    iget-object v0, v0, Landroid/support/constraint/solver/widgets/ConstraintAnchor;->mTarget:Landroid/support/constraint/solver/widgets/ConstraintAnchor;

    move-object/from16 v17, v0

    if-eqz v17, :cond_7

    .line 381
    iget-object v0, v10, Landroid/support/constraint/solver/widgets/ConstraintWidget;->mRight:Landroid/support/constraint/solver/widgets/ConstraintAnchor;

    move-object/from16 v17, v0

    move-object/from16 v0, v17

    iget-object v0, v0, Landroid/support/constraint/solver/widgets/ConstraintAnchor;->mTarget:Landroid/support/constraint/solver/widgets/ConstraintAnchor;

    move-object/from16 v17, v0

    move-object/from16 v0, v17

    iget-object v5, v0, Landroid/support/constraint/solver/widgets/ConstraintAnchor;->mOwner:Landroid/support/constraint/solver/widgets/ConstraintWidget;

    .line 382
    .local v5, "endTarget":Landroid/support/constraint/solver/widgets/ConstraintWidget;
    move-object/from16 v0, p0

    if-ne v5, v0, :cond_7

    .line 383
    invoke-virtual/range {p0 .. p0}, Landroid/support/constraint/solver/widgets/ConstraintWidgetContainer;->getRight()I

    move-result v8

    .line 387
    .end local v5    # "endTarget":Landroid/support/constraint/solver/widgets/ConstraintWidget;
    :cond_7
    sub-int v17, v8, v6

    move/from16 v0, v17

    int-to-float v14, v0

    .line 388
    .local v14, "total":F
    move/from16 v0, v16

    int-to-float v0, v0

    move/from16 v17, v0

    sub-float v13, v14, v17

    .line 389
    .local v13, "spreadSpace":F
    add-int/lit8 v17, v3, 0x1

    move/from16 v0, v17

    int-to-float v0, v0

    move/from16 v17, v0

    div-float v12, v13, v17

    .line 390
    .local v12, "split":F
    move-object/from16 p3, v7

    .line 391
    const/4 v4, 0x0

    .line 392
    .local v4, "currentPosition":F
    if-nez p2, :cond_c

    .line 393
    move v4, v12

    .line 399
    :cond_8
    :goto_6
    if-eqz p3, :cond_12

    .line 400
    move-object/from16 v0, p3

    iget-object v0, v0, Landroid/support/constraint/solver/widgets/ConstraintWidget;->mLeft:Landroid/support/constraint/solver/widgets/ConstraintAnchor;

    move-object/from16 v17, v0

    move-object/from16 v0, v17

    iget-object v0, v0, Landroid/support/constraint/solver/widgets/ConstraintAnchor;->mTarget:Landroid/support/constraint/solver/widgets/ConstraintAnchor;

    move-object/from16 v17, v0

    if-eqz v17, :cond_d

    move-object/from16 v0, p3

    iget-object v0, v0, Landroid/support/constraint/solver/widgets/ConstraintWidget;->mLeft:Landroid/support/constraint/solver/widgets/ConstraintAnchor;

    move-object/from16 v17, v0

    invoke-virtual/range {v17 .. v17}, Landroid/support/constraint/solver/widgets/ConstraintAnchor;->getMargin()I

    move-result v9

    .line 401
    .local v9, "left":I
    :goto_7
    move-object/from16 v0, p3

    iget-object v0, v0, Landroid/support/constraint/solver/widgets/ConstraintWidget;->mRight:Landroid/support/constraint/solver/widgets/ConstraintAnchor;

    move-object/from16 v17, v0

    move-object/from16 v0, v17

    iget-object v0, v0, Landroid/support/constraint/solver/widgets/ConstraintAnchor;->mTarget:Landroid/support/constraint/solver/widgets/ConstraintAnchor;

    move-object/from16 v17, v0

    if-eqz v17, :cond_e

    move-object/from16 v0, p3

    iget-object v0, v0, Landroid/support/constraint/solver/widgets/ConstraintWidget;->mRight:Landroid/support/constraint/solver/widgets/ConstraintAnchor;

    move-object/from16 v17, v0

    invoke-virtual/range {v17 .. v17}, Landroid/support/constraint/solver/widgets/ConstraintAnchor;->getMargin()I

    move-result v11

    .line 402
    .local v11, "right":I
    :goto_8
    int-to-float v0, v9

    move/from16 v17, v0

    add-float v4, v4, v17

    .line 403
    move-object/from16 v0, p3

    iget-object v0, v0, Landroid/support/constraint/solver/widgets/ConstraintWidget;->mLeft:Landroid/support/constraint/solver/widgets/ConstraintAnchor;

    move-object/from16 v17, v0

    move-object/from16 v0, v17

    iget-object v0, v0, Landroid/support/constraint/solver/widgets/ConstraintAnchor;->mSolverVariable:Landroid/support/constraint/solver/SolverVariable;

    move-object/from16 v17, v0

    float-to-int v0, v4

    move/from16 v18, v0

    move-object/from16 v0, p1

    move-object/from16 v1, v17

    move/from16 v2, v18

    invoke-virtual {v0, v1, v2}, Landroid/support/constraint/solver/LinearSystem;->addEquality(Landroid/support/constraint/solver/SolverVariable;I)V

    .line 404
    move-object/from16 v0, p3

    iget-object v0, v0, Landroid/support/constraint/solver/widgets/ConstraintWidget;->mHorizontalDimensionBehaviour:Landroid/support/constraint/solver/widgets/ConstraintWidget$DimensionBehaviour;

    move-object/from16 v17, v0

    sget-object v18, Landroid/support/constraint/solver/widgets/ConstraintWidget$DimensionBehaviour;->MATCH_CONSTRAINT:Landroid/support/constraint/solver/widgets/ConstraintWidget$DimensionBehaviour;

    move-object/from16 v0, v17

    move-object/from16 v1, v18

    if-ne v0, v1, :cond_10

    .line 405
    const/16 v17, 0x0

    cmpl-float v17, v15, v17

    if-nez v17, :cond_f

    .line 406
    int-to-float v0, v9

    move/from16 v17, v0

    sub-float v17, v12, v17

    int-to-float v0, v11

    move/from16 v18, v0

    sub-float v17, v17, v18

    add-float v4, v4, v17

    .line 413
    :goto_9
    move-object/from16 v0, p3

    iget-object v0, v0, Landroid/support/constraint/solver/widgets/ConstraintWidget;->mRight:Landroid/support/constraint/solver/widgets/ConstraintAnchor;

    move-object/from16 v17, v0

    move-object/from16 v0, v17

    iget-object v0, v0, Landroid/support/constraint/solver/widgets/ConstraintAnchor;->mSolverVariable:Landroid/support/constraint/solver/SolverVariable;

    move-object/from16 v17, v0

    float-to-int v0, v4

    move/from16 v18, v0

    move-object/from16 v0, p1

    move-object/from16 v1, v17

    move/from16 v2, v18

    invoke-virtual {v0, v1, v2}, Landroid/support/constraint/solver/LinearSystem;->addEquality(Landroid/support/constraint/solver/SolverVariable;I)V

    .line 414
    if-nez p2, :cond_9

    .line 415
    add-float/2addr v4, v12

    .line 417
    :cond_9
    int-to-float v0, v11

    move/from16 v17, v0

    add-float v4, v4, v17

    .line 418
    move-object/from16 v10, p3

    .line 419
    move-object/from16 v0, p3

    iget-object v0, v0, Landroid/support/constraint/solver/widgets/ConstraintWidget;->mRight:Landroid/support/constraint/solver/widgets/ConstraintAnchor;

    move-object/from16 v17, v0

    move-object/from16 v0, v17

    iget-object v0, v0, Landroid/support/constraint/solver/widgets/ConstraintAnchor;->mTarget:Landroid/support/constraint/solver/widgets/ConstraintAnchor;

    move-object/from16 v17, v0

    if-eqz v17, :cond_11

    move-object/from16 v0, p3

    iget-object v0, v0, Landroid/support/constraint/solver/widgets/ConstraintWidget;->mRight:Landroid/support/constraint/solver/widgets/ConstraintAnchor;

    move-object/from16 v17, v0

    move-object/from16 v0, v17

    iget-object v0, v0, Landroid/support/constraint/solver/widgets/ConstraintAnchor;->mTarget:Landroid/support/constraint/solver/widgets/ConstraintAnchor;

    move-object/from16 v17, v0

    move-object/from16 v0, v17

    iget-object v0, v0, Landroid/support/constraint/solver/widgets/ConstraintAnchor;->mOwner:Landroid/support/constraint/solver/widgets/ConstraintWidget;

    move-object/from16 p3, v0

    .line 420
    :goto_a
    if-eqz p3, :cond_a

    move-object/from16 v0, p3

    iget-object v0, v0, Landroid/support/constraint/solver/widgets/ConstraintWidget;->mLeft:Landroid/support/constraint/solver/widgets/ConstraintAnchor;

    move-object/from16 v17, v0

    move-object/from16 v0, v17

    iget-object v0, v0, Landroid/support/constraint/solver/widgets/ConstraintAnchor;->mTarget:Landroid/support/constraint/solver/widgets/ConstraintAnchor;

    move-object/from16 v17, v0

    if-eqz v17, :cond_a

    move-object/from16 v0, p3

    iget-object v0, v0, Landroid/support/constraint/solver/widgets/ConstraintWidget;->mLeft:Landroid/support/constraint/solver/widgets/ConstraintAnchor;

    move-object/from16 v17, v0

    move-object/from16 v0, v17

    iget-object v0, v0, Landroid/support/constraint/solver/widgets/ConstraintAnchor;->mTarget:Landroid/support/constraint/solver/widgets/ConstraintAnchor;

    move-object/from16 v17, v0

    move-object/from16 v0, v17

    iget-object v0, v0, Landroid/support/constraint/solver/widgets/ConstraintAnchor;->mOwner:Landroid/support/constraint/solver/widgets/ConstraintWidget;

    move-object/from16 v17, v0

    move-object/from16 v0, v17

    if-eq v0, v10, :cond_a

    .line 422
    const/16 p3, 0x0

    .line 424
    :cond_a
    move-object/from16 v0, p3

    move-object/from16 v1, p0

    if-ne v0, v1, :cond_8

    .line 425
    const/16 p3, 0x0

    goto/16 :goto_6

    .line 379
    .end local v4    # "currentPosition":F
    .end local v9    # "left":I
    .end local v11    # "right":I
    .end local v12    # "split":F
    .end local v13    # "spreadSpace":F
    .end local v14    # "total":F
    :cond_b
    const/4 v8, 0x0

    goto/16 :goto_5

    .line 395
    .restart local v4    # "currentPosition":F
    .restart local v12    # "split":F
    .restart local v13    # "spreadSpace":F
    .restart local v14    # "total":F
    :cond_c
    move/from16 v0, p2

    int-to-float v0, v0

    move/from16 v17, v0

    div-float v12, v13, v17

    goto/16 :goto_6

    .line 400
    :cond_d
    const/4 v9, 0x0

    goto/16 :goto_7

    .line 401
    .restart local v9    # "left":I
    :cond_e
    const/4 v11, 0x0

    goto/16 :goto_8

    .line 408
    .restart local v11    # "right":I
    :cond_f
    move-object/from16 v0, p3

    iget v0, v0, Landroid/support/constraint/solver/widgets/ConstraintWidget;->mHorizontalWeight:F

    move/from16 v17, v0

    mul-float v17, v17, v13

    div-float v17, v17, v15

    int-to-float v0, v9

    move/from16 v18, v0

    sub-float v17, v17, v18

    int-to-float v0, v11

    move/from16 v18, v0

    sub-float v17, v17, v18

    add-float v4, v4, v17

    goto/16 :goto_9

    .line 411
    :cond_10
    invoke-virtual/range {p3 .. p3}, Landroid/support/constraint/solver/widgets/ConstraintWidget;->getWidth()I

    move-result v17

    move/from16 v0, v17

    int-to-float v0, v0

    move/from16 v17, v0

    add-float v4, v4, v17

    goto/16 :goto_9

    .line 419
    :cond_11
    const/16 p3, 0x0

    goto :goto_a

    .line 428
    .end local v9    # "left":I
    .end local v11    # "right":I
    :cond_12
    return-void
.end method

.method private applyDirectResolutionVerticalChain(Landroid/support/constraint/solver/LinearSystem;ILandroid/support/constraint/solver/widgets/ConstraintWidget;)V
    .locals 19
    .param p1, "system"    # Landroid/support/constraint/solver/LinearSystem;
    .param p2, "numMatchConstraints"    # I
    .param p3, "widget"    # Landroid/support/constraint/solver/widgets/ConstraintWidget;

    .prologue
    .line 584
    move-object/from16 v8, p3

    .line 586
    .local v8, "firstWidget":Landroid/support/constraint/solver/widgets/ConstraintWidget;
    const/16 v16, 0x0

    .line 587
    .local v16, "widgetSize":I
    const/4 v7, 0x0

    .line 588
    .local v7, "firstPosition":I
    const/4 v10, 0x0

    .line 589
    .local v10, "previous":Landroid/support/constraint/solver/widgets/ConstraintWidget;
    const/4 v4, 0x0

    .line 590
    .local v4, "count":I
    const/4 v15, 0x0

    .line 593
    .local v15, "totalWeights":F
    :cond_0
    :goto_0
    if-eqz p3, :cond_6

    .line 594
    add-int/lit8 v4, v4, 0x1

    .line 595
    move-object/from16 v0, p3

    iget-object v0, v0, Landroid/support/constraint/solver/widgets/ConstraintWidget;->mVerticalDimensionBehaviour:Landroid/support/constraint/solver/widgets/ConstraintWidget$DimensionBehaviour;

    move-object/from16 v17, v0

    sget-object v18, Landroid/support/constraint/solver/widgets/ConstraintWidget$DimensionBehaviour;->MATCH_CONSTRAINT:Landroid/support/constraint/solver/widgets/ConstraintWidget$DimensionBehaviour;

    move-object/from16 v0, v17

    move-object/from16 v1, v18

    if-eq v0, v1, :cond_4

    .line 596
    invoke-virtual/range {p3 .. p3}, Landroid/support/constraint/solver/widgets/ConstraintWidget;->getHeight()I

    move-result v17

    add-int v16, v16, v17

    .line 597
    move-object/from16 v0, p3

    iget-object v0, v0, Landroid/support/constraint/solver/widgets/ConstraintWidget;->mTop:Landroid/support/constraint/solver/widgets/ConstraintAnchor;

    move-object/from16 v17, v0

    move-object/from16 v0, v17

    iget-object v0, v0, Landroid/support/constraint/solver/widgets/ConstraintAnchor;->mTarget:Landroid/support/constraint/solver/widgets/ConstraintAnchor;

    move-object/from16 v17, v0

    if-eqz v17, :cond_2

    move-object/from16 v0, p3

    iget-object v0, v0, Landroid/support/constraint/solver/widgets/ConstraintWidget;->mTop:Landroid/support/constraint/solver/widgets/ConstraintAnchor;

    move-object/from16 v17, v0

    invoke-virtual/range {v17 .. v17}, Landroid/support/constraint/solver/widgets/ConstraintAnchor;->getMargin()I

    move-result v17

    :goto_1
    add-int v16, v16, v17

    .line 598
    move-object/from16 v0, p3

    iget-object v0, v0, Landroid/support/constraint/solver/widgets/ConstraintWidget;->mBottom:Landroid/support/constraint/solver/widgets/ConstraintAnchor;

    move-object/from16 v17, v0

    move-object/from16 v0, v17

    iget-object v0, v0, Landroid/support/constraint/solver/widgets/ConstraintAnchor;->mTarget:Landroid/support/constraint/solver/widgets/ConstraintAnchor;

    move-object/from16 v17, v0

    if-eqz v17, :cond_3

    move-object/from16 v0, p3

    iget-object v0, v0, Landroid/support/constraint/solver/widgets/ConstraintWidget;->mBottom:Landroid/support/constraint/solver/widgets/ConstraintAnchor;

    move-object/from16 v17, v0

    invoke-virtual/range {v17 .. v17}, Landroid/support/constraint/solver/widgets/ConstraintAnchor;->getMargin()I

    move-result v17

    :goto_2
    add-int v16, v16, v17

    .line 602
    :goto_3
    move-object/from16 v10, p3

    .line 603
    move-object/from16 v0, p3

    iget-object v0, v0, Landroid/support/constraint/solver/widgets/ConstraintWidget;->mBottom:Landroid/support/constraint/solver/widgets/ConstraintAnchor;

    move-object/from16 v17, v0

    move-object/from16 v0, v17

    iget-object v0, v0, Landroid/support/constraint/solver/widgets/ConstraintAnchor;->mTarget:Landroid/support/constraint/solver/widgets/ConstraintAnchor;

    move-object/from16 v17, v0

    if-eqz v17, :cond_5

    move-object/from16 v0, p3

    iget-object v0, v0, Landroid/support/constraint/solver/widgets/ConstraintWidget;->mBottom:Landroid/support/constraint/solver/widgets/ConstraintAnchor;

    move-object/from16 v17, v0

    move-object/from16 v0, v17

    iget-object v0, v0, Landroid/support/constraint/solver/widgets/ConstraintAnchor;->mTarget:Landroid/support/constraint/solver/widgets/ConstraintAnchor;

    move-object/from16 v17, v0

    move-object/from16 v0, v17

    iget-object v0, v0, Landroid/support/constraint/solver/widgets/ConstraintAnchor;->mOwner:Landroid/support/constraint/solver/widgets/ConstraintWidget;

    move-object/from16 p3, v0

    .line 604
    :goto_4
    if-eqz p3, :cond_0

    move-object/from16 v0, p3

    iget-object v0, v0, Landroid/support/constraint/solver/widgets/ConstraintWidget;->mTop:Landroid/support/constraint/solver/widgets/ConstraintAnchor;

    move-object/from16 v17, v0

    move-object/from16 v0, v17

    iget-object v0, v0, Landroid/support/constraint/solver/widgets/ConstraintAnchor;->mTarget:Landroid/support/constraint/solver/widgets/ConstraintAnchor;

    move-object/from16 v17, v0

    if-eqz v17, :cond_1

    move-object/from16 v0, p3

    iget-object v0, v0, Landroid/support/constraint/solver/widgets/ConstraintWidget;->mTop:Landroid/support/constraint/solver/widgets/ConstraintAnchor;

    move-object/from16 v17, v0

    move-object/from16 v0, v17

    iget-object v0, v0, Landroid/support/constraint/solver/widgets/ConstraintAnchor;->mTarget:Landroid/support/constraint/solver/widgets/ConstraintAnchor;

    move-object/from16 v17, v0

    if-eqz v17, :cond_0

    move-object/from16 v0, p3

    iget-object v0, v0, Landroid/support/constraint/solver/widgets/ConstraintWidget;->mTop:Landroid/support/constraint/solver/widgets/ConstraintAnchor;

    move-object/from16 v17, v0

    move-object/from16 v0, v17

    iget-object v0, v0, Landroid/support/constraint/solver/widgets/ConstraintAnchor;->mTarget:Landroid/support/constraint/solver/widgets/ConstraintAnchor;

    move-object/from16 v17, v0

    move-object/from16 v0, v17

    iget-object v0, v0, Landroid/support/constraint/solver/widgets/ConstraintAnchor;->mOwner:Landroid/support/constraint/solver/widgets/ConstraintWidget;

    move-object/from16 v17, v0

    move-object/from16 v0, v17

    if-eq v0, v10, :cond_0

    .line 607
    :cond_1
    const/16 p3, 0x0

    goto/16 :goto_0

    .line 597
    :cond_2
    const/16 v17, 0x0

    goto :goto_1

    .line 598
    :cond_3
    const/16 v17, 0x0

    goto :goto_2

    .line 600
    :cond_4
    move-object/from16 v0, p3

    iget v0, v0, Landroid/support/constraint/solver/widgets/ConstraintWidget;->mVerticalWeight:F

    move/from16 v17, v0

    add-float v15, v15, v17

    goto :goto_3

    .line 603
    :cond_5
    const/16 p3, 0x0

    goto :goto_4

    .line 612
    :cond_6
    const/4 v9, 0x0

    .line 613
    .local v9, "lastPosition":I
    if-eqz v10, :cond_7

    .line 614
    iget-object v0, v10, Landroid/support/constraint/solver/widgets/ConstraintWidget;->mBottom:Landroid/support/constraint/solver/widgets/ConstraintAnchor;

    move-object/from16 v17, v0

    move-object/from16 v0, v17

    iget-object v0, v0, Landroid/support/constraint/solver/widgets/ConstraintAnchor;->mTarget:Landroid/support/constraint/solver/widgets/ConstraintAnchor;

    move-object/from16 v17, v0

    if-eqz v17, :cond_b

    iget-object v0, v10, Landroid/support/constraint/solver/widgets/ConstraintWidget;->mBottom:Landroid/support/constraint/solver/widgets/ConstraintAnchor;

    move-object/from16 v17, v0

    move-object/from16 v0, v17

    iget-object v0, v0, Landroid/support/constraint/solver/widgets/ConstraintAnchor;->mTarget:Landroid/support/constraint/solver/widgets/ConstraintAnchor;

    move-object/from16 v17, v0

    move-object/from16 v0, v17

    iget-object v0, v0, Landroid/support/constraint/solver/widgets/ConstraintAnchor;->mOwner:Landroid/support/constraint/solver/widgets/ConstraintWidget;

    move-object/from16 v17, v0

    invoke-virtual/range {v17 .. v17}, Landroid/support/constraint/solver/widgets/ConstraintWidget;->getX()I

    move-result v9

    .line 615
    :goto_5
    iget-object v0, v10, Landroid/support/constraint/solver/widgets/ConstraintWidget;->mBottom:Landroid/support/constraint/solver/widgets/ConstraintAnchor;

    move-object/from16 v17, v0

    move-object/from16 v0, v17

    iget-object v0, v0, Landroid/support/constraint/solver/widgets/ConstraintAnchor;->mTarget:Landroid/support/constraint/solver/widgets/ConstraintAnchor;

    move-object/from16 v17, v0

    if-eqz v17, :cond_7

    .line 616
    iget-object v0, v10, Landroid/support/constraint/solver/widgets/ConstraintWidget;->mBottom:Landroid/support/constraint/solver/widgets/ConstraintAnchor;

    move-object/from16 v17, v0

    move-object/from16 v0, v17

    iget-object v0, v0, Landroid/support/constraint/solver/widgets/ConstraintAnchor;->mTarget:Landroid/support/constraint/solver/widgets/ConstraintAnchor;

    move-object/from16 v17, v0

    move-object/from16 v0, v17

    iget-object v6, v0, Landroid/support/constraint/solver/widgets/ConstraintAnchor;->mOwner:Landroid/support/constraint/solver/widgets/ConstraintWidget;

    .line 617
    .local v6, "endTarget":Landroid/support/constraint/solver/widgets/ConstraintWidget;
    move-object/from16 v0, p0

    if-ne v6, v0, :cond_7

    .line 618
    invoke-virtual/range {p0 .. p0}, Landroid/support/constraint/solver/widgets/ConstraintWidgetContainer;->getBottom()I

    move-result v9

    .line 622
    .end local v6    # "endTarget":Landroid/support/constraint/solver/widgets/ConstraintWidget;
    :cond_7
    sub-int v17, v9, v7

    move/from16 v0, v17

    int-to-float v14, v0

    .line 623
    .local v14, "total":F
    move/from16 v0, v16

    int-to-float v0, v0

    move/from16 v17, v0

    sub-float v12, v14, v17

    .line 624
    .local v12, "spreadSpace":F
    add-int/lit8 v17, v4, 0x1

    move/from16 v0, v17

    int-to-float v0, v0

    move/from16 v17, v0

    div-float v11, v12, v17

    .line 625
    .local v11, "split":F
    move-object/from16 p3, v8

    .line 626
    const/4 v5, 0x0

    .line 627
    .local v5, "currentPosition":F
    if-nez p2, :cond_c

    .line 628
    move v5, v11

    .line 634
    :cond_8
    :goto_6
    if-eqz p3, :cond_12

    .line 635
    move-object/from16 v0, p3

    iget-object v0, v0, Landroid/support/constraint/solver/widgets/ConstraintWidget;->mTop:Landroid/support/constraint/solver/widgets/ConstraintAnchor;

    move-object/from16 v17, v0

    move-object/from16 v0, v17

    iget-object v0, v0, Landroid/support/constraint/solver/widgets/ConstraintAnchor;->mTarget:Landroid/support/constraint/solver/widgets/ConstraintAnchor;

    move-object/from16 v17, v0

    if-eqz v17, :cond_d

    move-object/from16 v0, p3

    iget-object v0, v0, Landroid/support/constraint/solver/widgets/ConstraintWidget;->mTop:Landroid/support/constraint/solver/widgets/ConstraintAnchor;

    move-object/from16 v17, v0

    invoke-virtual/range {v17 .. v17}, Landroid/support/constraint/solver/widgets/ConstraintAnchor;->getMargin()I

    move-result v13

    .line 636
    .local v13, "top":I
    :goto_7
    move-object/from16 v0, p3

    iget-object v0, v0, Landroid/support/constraint/solver/widgets/ConstraintWidget;->mBottom:Landroid/support/constraint/solver/widgets/ConstraintAnchor;

    move-object/from16 v17, v0

    move-object/from16 v0, v17

    iget-object v0, v0, Landroid/support/constraint/solver/widgets/ConstraintAnchor;->mTarget:Landroid/support/constraint/solver/widgets/ConstraintAnchor;

    move-object/from16 v17, v0

    if-eqz v17, :cond_e

    move-object/from16 v0, p3

    iget-object v0, v0, Landroid/support/constraint/solver/widgets/ConstraintWidget;->mBottom:Landroid/support/constraint/solver/widgets/ConstraintAnchor;

    move-object/from16 v17, v0

    invoke-virtual/range {v17 .. v17}, Landroid/support/constraint/solver/widgets/ConstraintAnchor;->getMargin()I

    move-result v3

    .line 637
    .local v3, "bottom":I
    :goto_8
    int-to-float v0, v13

    move/from16 v17, v0

    add-float v5, v5, v17

    .line 638
    move-object/from16 v0, p3

    iget-object v0, v0, Landroid/support/constraint/solver/widgets/ConstraintWidget;->mTop:Landroid/support/constraint/solver/widgets/ConstraintAnchor;

    move-object/from16 v17, v0

    move-object/from16 v0, v17

    iget-object v0, v0, Landroid/support/constraint/solver/widgets/ConstraintAnchor;->mSolverVariable:Landroid/support/constraint/solver/SolverVariable;

    move-object/from16 v17, v0

    float-to-int v0, v5

    move/from16 v18, v0

    move-object/from16 v0, p1

    move-object/from16 v1, v17

    move/from16 v2, v18

    invoke-virtual {v0, v1, v2}, Landroid/support/constraint/solver/LinearSystem;->addEquality(Landroid/support/constraint/solver/SolverVariable;I)V

    .line 639
    move-object/from16 v0, p3

    iget-object v0, v0, Landroid/support/constraint/solver/widgets/ConstraintWidget;->mVerticalDimensionBehaviour:Landroid/support/constraint/solver/widgets/ConstraintWidget$DimensionBehaviour;

    move-object/from16 v17, v0

    sget-object v18, Landroid/support/constraint/solver/widgets/ConstraintWidget$DimensionBehaviour;->MATCH_CONSTRAINT:Landroid/support/constraint/solver/widgets/ConstraintWidget$DimensionBehaviour;

    move-object/from16 v0, v17

    move-object/from16 v1, v18

    if-ne v0, v1, :cond_10

    .line 640
    const/16 v17, 0x0

    cmpl-float v17, v15, v17

    if-nez v17, :cond_f

    .line 641
    int-to-float v0, v13

    move/from16 v17, v0

    sub-float v17, v11, v17

    int-to-float v0, v3

    move/from16 v18, v0

    sub-float v17, v17, v18

    add-float v5, v5, v17

    .line 648
    :goto_9
    move-object/from16 v0, p3

    iget-object v0, v0, Landroid/support/constraint/solver/widgets/ConstraintWidget;->mBottom:Landroid/support/constraint/solver/widgets/ConstraintAnchor;

    move-object/from16 v17, v0

    move-object/from16 v0, v17

    iget-object v0, v0, Landroid/support/constraint/solver/widgets/ConstraintAnchor;->mSolverVariable:Landroid/support/constraint/solver/SolverVariable;

    move-object/from16 v17, v0

    float-to-int v0, v5

    move/from16 v18, v0

    move-object/from16 v0, p1

    move-object/from16 v1, v17

    move/from16 v2, v18

    invoke-virtual {v0, v1, v2}, Landroid/support/constraint/solver/LinearSystem;->addEquality(Landroid/support/constraint/solver/SolverVariable;I)V

    .line 649
    if-nez p2, :cond_9

    .line 650
    add-float/2addr v5, v11

    .line 652
    :cond_9
    int-to-float v0, v3

    move/from16 v17, v0

    add-float v5, v5, v17

    .line 653
    move-object/from16 v10, p3

    .line 654
    move-object/from16 v0, p3

    iget-object v0, v0, Landroid/support/constraint/solver/widgets/ConstraintWidget;->mBottom:Landroid/support/constraint/solver/widgets/ConstraintAnchor;

    move-object/from16 v17, v0

    move-object/from16 v0, v17

    iget-object v0, v0, Landroid/support/constraint/solver/widgets/ConstraintAnchor;->mTarget:Landroid/support/constraint/solver/widgets/ConstraintAnchor;

    move-object/from16 v17, v0

    if-eqz v17, :cond_11

    move-object/from16 v0, p3

    iget-object v0, v0, Landroid/support/constraint/solver/widgets/ConstraintWidget;->mBottom:Landroid/support/constraint/solver/widgets/ConstraintAnchor;

    move-object/from16 v17, v0

    move-object/from16 v0, v17

    iget-object v0, v0, Landroid/support/constraint/solver/widgets/ConstraintAnchor;->mTarget:Landroid/support/constraint/solver/widgets/ConstraintAnchor;

    move-object/from16 v17, v0

    move-object/from16 v0, v17

    iget-object v0, v0, Landroid/support/constraint/solver/widgets/ConstraintAnchor;->mOwner:Landroid/support/constraint/solver/widgets/ConstraintWidget;

    move-object/from16 p3, v0

    .line 655
    :goto_a
    if-eqz p3, :cond_a

    move-object/from16 v0, p3

    iget-object v0, v0, Landroid/support/constraint/solver/widgets/ConstraintWidget;->mTop:Landroid/support/constraint/solver/widgets/ConstraintAnchor;

    move-object/from16 v17, v0

    move-object/from16 v0, v17

    iget-object v0, v0, Landroid/support/constraint/solver/widgets/ConstraintAnchor;->mTarget:Landroid/support/constraint/solver/widgets/ConstraintAnchor;

    move-object/from16 v17, v0

    if-eqz v17, :cond_a

    move-object/from16 v0, p3

    iget-object v0, v0, Landroid/support/constraint/solver/widgets/ConstraintWidget;->mTop:Landroid/support/constraint/solver/widgets/ConstraintAnchor;

    move-object/from16 v17, v0

    move-object/from16 v0, v17

    iget-object v0, v0, Landroid/support/constraint/solver/widgets/ConstraintAnchor;->mTarget:Landroid/support/constraint/solver/widgets/ConstraintAnchor;

    move-object/from16 v17, v0

    move-object/from16 v0, v17

    iget-object v0, v0, Landroid/support/constraint/solver/widgets/ConstraintAnchor;->mOwner:Landroid/support/constraint/solver/widgets/ConstraintWidget;

    move-object/from16 v17, v0

    move-object/from16 v0, v17

    if-eq v0, v10, :cond_a

    .line 657
    const/16 p3, 0x0

    .line 659
    :cond_a
    move-object/from16 v0, p3

    move-object/from16 v1, p0

    if-ne v0, v1, :cond_8

    .line 660
    const/16 p3, 0x0

    goto/16 :goto_6

    .line 614
    .end local v3    # "bottom":I
    .end local v5    # "currentPosition":F
    .end local v11    # "split":F
    .end local v12    # "spreadSpace":F
    .end local v13    # "top":I
    .end local v14    # "total":F
    :cond_b
    const/4 v9, 0x0

    goto/16 :goto_5

    .line 630
    .restart local v5    # "currentPosition":F
    .restart local v11    # "split":F
    .restart local v12    # "spreadSpace":F
    .restart local v14    # "total":F
    :cond_c
    move/from16 v0, p2

    int-to-float v0, v0

    move/from16 v17, v0

    div-float v11, v12, v17

    goto/16 :goto_6

    .line 635
    :cond_d
    const/4 v13, 0x0

    goto/16 :goto_7

    .line 636
    .restart local v13    # "top":I
    :cond_e
    const/4 v3, 0x0

    goto/16 :goto_8

    .line 643
    .restart local v3    # "bottom":I
    :cond_f
    move-object/from16 v0, p3

    iget v0, v0, Landroid/support/constraint/solver/widgets/ConstraintWidget;->mVerticalWeight:F

    move/from16 v17, v0

    mul-float v17, v17, v12

    div-float v17, v17, v15

    int-to-float v0, v13

    move/from16 v18, v0

    sub-float v17, v17, v18

    int-to-float v0, v3

    move/from16 v18, v0

    sub-float v17, v17, v18

    add-float v5, v5, v17

    goto/16 :goto_9

    .line 646
    :cond_10
    invoke-virtual/range {p3 .. p3}, Landroid/support/constraint/solver/widgets/ConstraintWidget;->getHeight()I

    move-result v17

    move/from16 v0, v17

    int-to-float v0, v0

    move/from16 v17, v0

    add-float v5, v5, v17

    goto/16 :goto_9

    .line 654
    :cond_11
    const/16 p3, 0x0

    goto :goto_a

    .line 663
    .end local v3    # "bottom":I
    .end local v13    # "top":I
    :cond_12
    return-void
.end method

.method private applyHorizontalChain(Landroid/support/constraint/solver/LinearSystem;)V
    .locals 36
    .param p1, "system"    # Landroid/support/constraint/solver/LinearSystem;

    .prologue
    .line 201
    const/16 v27, 0x0

    .local v27, "i":I
    :goto_0
    move-object/from16 v0, p0

    iget v4, v0, Landroid/support/constraint/solver/widgets/ConstraintWidgetContainer;->mHorizontalChainsSize:I

    move/from16 v0, v27

    if-ge v0, v4, :cond_1c

    .line 202
    move-object/from16 v0, p0

    iget-object v4, v0, Landroid/support/constraint/solver/widgets/ConstraintWidgetContainer;->mHorizontalChainsArray:[Landroid/support/constraint/solver/widgets/ConstraintWidget;

    aget-object v26, v4, v27

    .line 203
    .local v26, "first":Landroid/support/constraint/solver/widgets/ConstraintWidget;
    move-object/from16 v0, p0

    iget-object v4, v0, Landroid/support/constraint/solver/widgets/ConstraintWidgetContainer;->mHorizontalChainsArray:[Landroid/support/constraint/solver/widgets/ConstraintWidget;

    aget-object v4, v4, v27

    const/4 v8, 0x0

    move-object/from16 v0, p0

    invoke-direct {v0, v4, v8}, Landroid/support/constraint/solver/widgets/ConstraintWidgetContainer;->countMatchConstraintsChainedWidgets(Landroid/support/constraint/solver/widgets/ConstraintWidget;I)I

    move-result v32

    .line 205
    .local v32, "numMatchConstraints":I
    move-object/from16 v0, v26

    iget-boolean v4, v0, Landroid/support/constraint/solver/widgets/ConstraintWidget;->mHorizontalChainPacked:Z

    if-eqz v4, :cond_1

    if-nez v32, :cond_1

    const/16 v24, 0x1

    .line 206
    .local v24, "chainPacked":Z
    :goto_1
    move-object/from16 v35, v26

    .line 207
    .local v35, "widget":Landroid/support/constraint/solver/widgets/ConstraintWidget;
    move-object/from16 v0, v35

    iget-boolean v4, v0, Landroid/support/constraint/solver/widgets/ConstraintWidget;->mHorizontalChainFixedPosition:Z

    if-eqz v4, :cond_2

    if-nez v24, :cond_2

    .line 208
    move-object/from16 v0, p0

    move-object/from16 v1, p1

    move/from16 v2, v32

    move-object/from16 v3, v35

    invoke-direct {v0, v1, v2, v3}, Landroid/support/constraint/solver/widgets/ConstraintWidgetContainer;->applyDirectResolutionHorizontalChain(Landroid/support/constraint/solver/LinearSystem;ILandroid/support/constraint/solver/widgets/ConstraintWidget;)V

    .line 201
    :cond_0
    :goto_2
    add-int/lit8 v27, v27, 0x1

    goto :goto_0

    .line 205
    .end local v24    # "chainPacked":Z
    .end local v35    # "widget":Landroid/support/constraint/solver/widgets/ConstraintWidget;
    :cond_1
    const/16 v24, 0x0

    goto :goto_1

    .line 210
    .restart local v24    # "chainPacked":Z
    .restart local v35    # "widget":Landroid/support/constraint/solver/widgets/ConstraintWidget;
    :cond_2
    if-nez v32, :cond_10

    .line 211
    const/16 v33, 0x0

    .line 212
    .local v33, "previous":Landroid/support/constraint/solver/widgets/ConstraintWidget;
    :goto_3
    if-eqz v33, :cond_3

    move-object/from16 v0, v35

    iget-object v4, v0, Landroid/support/constraint/solver/widgets/ConstraintWidget;->mLeft:Landroid/support/constraint/solver/widgets/ConstraintAnchor;

    iget-object v4, v4, Landroid/support/constraint/solver/widgets/ConstraintAnchor;->mTarget:Landroid/support/constraint/solver/widgets/ConstraintAnchor;

    if-eqz v4, :cond_d

    move-object/from16 v0, v35

    iget-object v4, v0, Landroid/support/constraint/solver/widgets/ConstraintWidget;->mLeft:Landroid/support/constraint/solver/widgets/ConstraintAnchor;

    iget-object v4, v4, Landroid/support/constraint/solver/widgets/ConstraintAnchor;->mTarget:Landroid/support/constraint/solver/widgets/ConstraintAnchor;

    iget-object v4, v4, Landroid/support/constraint/solver/widgets/ConstraintAnchor;->mOwner:Landroid/support/constraint/solver/widgets/ConstraintWidget;

    move-object/from16 v0, v33

    if-ne v4, v0, :cond_d

    .line 214
    :cond_3
    move-object/from16 v0, v35

    iget-object v4, v0, Landroid/support/constraint/solver/widgets/ConstraintWidget;->mLeft:Landroid/support/constraint/solver/widgets/ConstraintAnchor;

    invoke-virtual {v4}, Landroid/support/constraint/solver/widgets/ConstraintAnchor;->getMargin()I

    move-result v7

    .line 215
    .local v7, "leftMargin":I
    move-object/from16 v0, v35

    iget-object v4, v0, Landroid/support/constraint/solver/widgets/ConstraintWidget;->mRight:Landroid/support/constraint/solver/widgets/ConstraintAnchor;

    invoke-virtual {v4}, Landroid/support/constraint/solver/widgets/ConstraintAnchor;->getMargin()I

    move-result v11

    .line 216
    .local v11, "rightMargin":I
    move-object/from16 v0, v35

    iget-object v4, v0, Landroid/support/constraint/solver/widgets/ConstraintWidget;->mLeft:Landroid/support/constraint/solver/widgets/ConstraintAnchor;

    iget-object v5, v4, Landroid/support/constraint/solver/widgets/ConstraintAnchor;->mSolverVariable:Landroid/support/constraint/solver/SolverVariable;

    .line 217
    .local v5, "left":Landroid/support/constraint/solver/SolverVariable;
    move-object/from16 v0, v35

    iget-object v4, v0, Landroid/support/constraint/solver/widgets/ConstraintWidget;->mLeft:Landroid/support/constraint/solver/widgets/ConstraintAnchor;

    iget-object v4, v4, Landroid/support/constraint/solver/widgets/ConstraintAnchor;->mTarget:Landroid/support/constraint/solver/widgets/ConstraintAnchor;

    if-eqz v4, :cond_8

    move-object/from16 v0, v35

    iget-object v4, v0, Landroid/support/constraint/solver/widgets/ConstraintWidget;->mLeft:Landroid/support/constraint/solver/widgets/ConstraintAnchor;

    iget-object v4, v4, Landroid/support/constraint/solver/widgets/ConstraintAnchor;->mTarget:Landroid/support/constraint/solver/widgets/ConstraintAnchor;

    iget-object v6, v4, Landroid/support/constraint/solver/widgets/ConstraintAnchor;->mSolverVariable:Landroid/support/constraint/solver/SolverVariable;

    .line 218
    .local v6, "leftTarget":Landroid/support/constraint/solver/SolverVariable;
    :goto_4
    move-object/from16 v0, v35

    iget-object v4, v0, Landroid/support/constraint/solver/widgets/ConstraintWidget;->mRight:Landroid/support/constraint/solver/widgets/ConstraintAnchor;

    iget-object v10, v4, Landroid/support/constraint/solver/widgets/ConstraintAnchor;->mSolverVariable:Landroid/support/constraint/solver/SolverVariable;

    .line 219
    .local v10, "right":Landroid/support/constraint/solver/SolverVariable;
    move-object/from16 v0, v35

    iget-object v4, v0, Landroid/support/constraint/solver/widgets/ConstraintWidget;->mRight:Landroid/support/constraint/solver/widgets/ConstraintAnchor;

    iget-object v4, v4, Landroid/support/constraint/solver/widgets/ConstraintAnchor;->mTarget:Landroid/support/constraint/solver/widgets/ConstraintAnchor;

    if-eqz v4, :cond_9

    move-object/from16 v0, v35

    iget-object v4, v0, Landroid/support/constraint/solver/widgets/ConstraintWidget;->mRight:Landroid/support/constraint/solver/widgets/ConstraintAnchor;

    iget-object v4, v4, Landroid/support/constraint/solver/widgets/ConstraintAnchor;->mTarget:Landroid/support/constraint/solver/widgets/ConstraintAnchor;

    iget-object v9, v4, Landroid/support/constraint/solver/widgets/ConstraintAnchor;->mSolverVariable:Landroid/support/constraint/solver/SolverVariable;

    .line 220
    .local v9, "rightTarget":Landroid/support/constraint/solver/SolverVariable;
    :goto_5
    move/from16 v29, v7

    .line 221
    .local v29, "margin":I
    if-eqz v33, :cond_4

    .line 222
    move-object/from16 v0, v33

    iget-object v4, v0, Landroid/support/constraint/solver/widgets/ConstraintWidget;->mRight:Landroid/support/constraint/solver/widgets/ConstraintAnchor;

    invoke-virtual {v4}, Landroid/support/constraint/solver/widgets/ConstraintAnchor;->getMargin()I

    move-result v4

    add-int v29, v29, v4

    .line 224
    :cond_4
    if-eqz v6, :cond_5

    .line 225
    if-eqz v24, :cond_a

    move-object/from16 v0, v35

    move-object/from16 v1, v26

    if-eq v0, v1, :cond_a

    .line 226
    move-object/from16 v0, p1

    move/from16 v1, v29

    invoke-virtual {v0, v5, v6, v1}, Landroid/support/constraint/solver/LinearSystem;->addEquality(Landroid/support/constraint/solver/SolverVariable;Landroid/support/constraint/solver/SolverVariable;I)V

    .line 231
    :cond_5
    :goto_6
    if-eqz v9, :cond_7

    .line 232
    move/from16 v29, v11

    .line 233
    move-object/from16 v0, v35

    iget-object v4, v0, Landroid/support/constraint/solver/widgets/ConstraintWidget;->mRight:Landroid/support/constraint/solver/widgets/ConstraintAnchor;

    iget-object v4, v4, Landroid/support/constraint/solver/widgets/ConstraintAnchor;->mTarget:Landroid/support/constraint/solver/widgets/ConstraintAnchor;

    iget-object v4, v4, Landroid/support/constraint/solver/widgets/ConstraintAnchor;->mOwner:Landroid/support/constraint/solver/widgets/ConstraintWidget;

    iget-object v0, v4, Landroid/support/constraint/solver/widgets/ConstraintWidget;->mLeft:Landroid/support/constraint/solver/widgets/ConstraintAnchor;

    move-object/from16 v20, v0

    .line 234
    .local v20, "nextLeft":Landroid/support/constraint/solver/widgets/ConstraintAnchor;
    move-object/from16 v0, v20

    iget-object v4, v0, Landroid/support/constraint/solver/widgets/ConstraintAnchor;->mTarget:Landroid/support/constraint/solver/widgets/ConstraintAnchor;

    if-eqz v4, :cond_b

    move-object/from16 v0, v20

    iget-object v4, v0, Landroid/support/constraint/solver/widgets/ConstraintAnchor;->mTarget:Landroid/support/constraint/solver/widgets/ConstraintAnchor;

    iget-object v0, v4, Landroid/support/constraint/solver/widgets/ConstraintAnchor;->mOwner:Landroid/support/constraint/solver/widgets/ConstraintWidget;

    move-object/from16 v30, v0

    .line 235
    .local v30, "nextLeftTarget":Landroid/support/constraint/solver/widgets/ConstraintWidget;
    :goto_7
    move-object/from16 v0, v30

    move-object/from16 v1, v35

    if-ne v0, v1, :cond_6

    .line 236
    invoke-virtual/range {v20 .. v20}, Landroid/support/constraint/solver/widgets/ConstraintAnchor;->getMargin()I

    move-result v4

    add-int v29, v29, v4

    .line 238
    :cond_6
    if-eqz v24, :cond_c

    move-object/from16 v0, v30

    move-object/from16 v1, v35

    if-ne v0, v1, :cond_c

    .line 239
    move/from16 v0, v29

    neg-int v4, v0

    move-object/from16 v0, p1

    invoke-virtual {v0, v10, v9, v4}, Landroid/support/constraint/solver/LinearSystem;->addEquality(Landroid/support/constraint/solver/SolverVariable;Landroid/support/constraint/solver/SolverVariable;I)V

    .line 243
    :goto_8
    if-nez v24, :cond_7

    if-eqz v6, :cond_7

    .line 244
    const/high16 v8, 0x3f000000    # 0.5f

    move-object/from16 v4, p1

    invoke-virtual/range {v4 .. v11}, Landroid/support/constraint/solver/LinearSystem;->addCentering(Landroid/support/constraint/solver/SolverVariable;Landroid/support/constraint/solver/SolverVariable;IFLandroid/support/constraint/solver/SolverVariable;Landroid/support/constraint/solver/SolverVariable;I)V

    .line 248
    .end local v20    # "nextLeft":Landroid/support/constraint/solver/widgets/ConstraintAnchor;
    .end local v30    # "nextLeftTarget":Landroid/support/constraint/solver/widgets/ConstraintWidget;
    :cond_7
    move-object/from16 v33, v35

    .line 249
    if-eqz v9, :cond_d

    .line 250
    move-object/from16 v0, v35

    iget-object v4, v0, Landroid/support/constraint/solver/widgets/ConstraintWidget;->mRight:Landroid/support/constraint/solver/widgets/ConstraintAnchor;

    iget-object v4, v4, Landroid/support/constraint/solver/widgets/ConstraintAnchor;->mTarget:Landroid/support/constraint/solver/widgets/ConstraintAnchor;

    iget-object v0, v4, Landroid/support/constraint/solver/widgets/ConstraintAnchor;->mOwner:Landroid/support/constraint/solver/widgets/ConstraintWidget;

    move-object/from16 v35, v0

    .line 254
    goto/16 :goto_3

    .line 217
    .end local v6    # "leftTarget":Landroid/support/constraint/solver/SolverVariable;
    .end local v9    # "rightTarget":Landroid/support/constraint/solver/SolverVariable;
    .end local v10    # "right":Landroid/support/constraint/solver/SolverVariable;
    .end local v29    # "margin":I
    :cond_8
    const/4 v6, 0x0

    goto/16 :goto_4

    .line 219
    .restart local v6    # "leftTarget":Landroid/support/constraint/solver/SolverVariable;
    .restart local v10    # "right":Landroid/support/constraint/solver/SolverVariable;
    :cond_9
    const/4 v9, 0x0

    goto :goto_5

    .line 228
    .restart local v9    # "rightTarget":Landroid/support/constraint/solver/SolverVariable;
    .restart local v29    # "margin":I
    :cond_a
    move-object/from16 v0, p1

    move/from16 v1, v29

    invoke-virtual {v0, v5, v6, v1}, Landroid/support/constraint/solver/LinearSystem;->addGreaterThan(Landroid/support/constraint/solver/SolverVariable;Landroid/support/constraint/solver/SolverVariable;I)V

    goto :goto_6

    .line 234
    .restart local v20    # "nextLeft":Landroid/support/constraint/solver/widgets/ConstraintAnchor;
    :cond_b
    const/16 v30, 0x0

    goto :goto_7

    .line 241
    .restart local v30    # "nextLeftTarget":Landroid/support/constraint/solver/widgets/ConstraintWidget;
    :cond_c
    move/from16 v0, v29

    neg-int v4, v0

    move-object/from16 v0, p1

    invoke-virtual {v0, v10, v9, v4}, Landroid/support/constraint/solver/LinearSystem;->addLowerThan(Landroid/support/constraint/solver/SolverVariable;Landroid/support/constraint/solver/SolverVariable;I)V

    goto :goto_8

    .line 255
    .end local v5    # "left":Landroid/support/constraint/solver/SolverVariable;
    .end local v6    # "leftTarget":Landroid/support/constraint/solver/SolverVariable;
    .end local v7    # "leftMargin":I
    .end local v9    # "rightTarget":Landroid/support/constraint/solver/SolverVariable;
    .end local v10    # "right":Landroid/support/constraint/solver/SolverVariable;
    .end local v11    # "rightMargin":I
    .end local v20    # "nextLeft":Landroid/support/constraint/solver/widgets/ConstraintAnchor;
    .end local v29    # "margin":I
    .end local v30    # "nextLeftTarget":Landroid/support/constraint/solver/widgets/ConstraintWidget;
    :cond_d
    if-eqz v24, :cond_0

    .line 256
    move-object/from16 v0, v26

    iget-object v4, v0, Landroid/support/constraint/solver/widgets/ConstraintWidget;->mLeft:Landroid/support/constraint/solver/widgets/ConstraintAnchor;

    invoke-virtual {v4}, Landroid/support/constraint/solver/widgets/ConstraintAnchor;->getMargin()I

    move-result v7

    .line 257
    .restart local v7    # "leftMargin":I
    move-object/from16 v0, v33

    iget-object v4, v0, Landroid/support/constraint/solver/widgets/ConstraintWidget;->mRight:Landroid/support/constraint/solver/widgets/ConstraintAnchor;

    invoke-virtual {v4}, Landroid/support/constraint/solver/widgets/ConstraintAnchor;->getMargin()I

    move-result v11

    .line 258
    .restart local v11    # "rightMargin":I
    move-object/from16 v0, v26

    iget-object v4, v0, Landroid/support/constraint/solver/widgets/ConstraintWidget;->mLeft:Landroid/support/constraint/solver/widgets/ConstraintAnchor;

    iget-object v5, v4, Landroid/support/constraint/solver/widgets/ConstraintAnchor;->mSolverVariable:Landroid/support/constraint/solver/SolverVariable;

    .line 259
    .restart local v5    # "left":Landroid/support/constraint/solver/SolverVariable;
    move-object/from16 v0, v26

    iget-object v4, v0, Landroid/support/constraint/solver/widgets/ConstraintWidget;->mLeft:Landroid/support/constraint/solver/widgets/ConstraintAnchor;

    iget-object v4, v4, Landroid/support/constraint/solver/widgets/ConstraintAnchor;->mTarget:Landroid/support/constraint/solver/widgets/ConstraintAnchor;

    if-eqz v4, :cond_e

    move-object/from16 v0, v26

    iget-object v4, v0, Landroid/support/constraint/solver/widgets/ConstraintWidget;->mLeft:Landroid/support/constraint/solver/widgets/ConstraintAnchor;

    iget-object v4, v4, Landroid/support/constraint/solver/widgets/ConstraintAnchor;->mTarget:Landroid/support/constraint/solver/widgets/ConstraintAnchor;

    iget-object v6, v4, Landroid/support/constraint/solver/widgets/ConstraintAnchor;->mSolverVariable:Landroid/support/constraint/solver/SolverVariable;

    .line 260
    .restart local v6    # "leftTarget":Landroid/support/constraint/solver/SolverVariable;
    :goto_9
    move-object/from16 v0, v33

    iget-object v4, v0, Landroid/support/constraint/solver/widgets/ConstraintWidget;->mRight:Landroid/support/constraint/solver/widgets/ConstraintAnchor;

    iget-object v10, v4, Landroid/support/constraint/solver/widgets/ConstraintAnchor;->mSolverVariable:Landroid/support/constraint/solver/SolverVariable;

    .line 261
    .restart local v10    # "right":Landroid/support/constraint/solver/SolverVariable;
    move-object/from16 v0, v33

    iget-object v4, v0, Landroid/support/constraint/solver/widgets/ConstraintWidget;->mRight:Landroid/support/constraint/solver/widgets/ConstraintAnchor;

    iget-object v4, v4, Landroid/support/constraint/solver/widgets/ConstraintAnchor;->mTarget:Landroid/support/constraint/solver/widgets/ConstraintAnchor;

    if-eqz v4, :cond_f

    move-object/from16 v0, v33

    iget-object v4, v0, Landroid/support/constraint/solver/widgets/ConstraintWidget;->mRight:Landroid/support/constraint/solver/widgets/ConstraintAnchor;

    iget-object v4, v4, Landroid/support/constraint/solver/widgets/ConstraintAnchor;->mTarget:Landroid/support/constraint/solver/widgets/ConstraintAnchor;

    iget-object v9, v4, Landroid/support/constraint/solver/widgets/ConstraintAnchor;->mSolverVariable:Landroid/support/constraint/solver/SolverVariable;

    .line 262
    .restart local v9    # "rightTarget":Landroid/support/constraint/solver/SolverVariable;
    :goto_a
    move-object/from16 v0, v26

    iget v8, v0, Landroid/support/constraint/solver/widgets/ConstraintWidget;->mHorizontalBiasPercent:F

    move-object/from16 v4, p1

    invoke-virtual/range {v4 .. v11}, Landroid/support/constraint/solver/LinearSystem;->addCentering(Landroid/support/constraint/solver/SolverVariable;Landroid/support/constraint/solver/SolverVariable;IFLandroid/support/constraint/solver/SolverVariable;Landroid/support/constraint/solver/SolverVariable;I)V

    goto/16 :goto_2

    .line 259
    .end local v6    # "leftTarget":Landroid/support/constraint/solver/SolverVariable;
    .end local v9    # "rightTarget":Landroid/support/constraint/solver/SolverVariable;
    .end local v10    # "right":Landroid/support/constraint/solver/SolverVariable;
    :cond_e
    const/4 v6, 0x0

    goto :goto_9

    .line 261
    .restart local v6    # "leftTarget":Landroid/support/constraint/solver/SolverVariable;
    .restart local v10    # "right":Landroid/support/constraint/solver/SolverVariable;
    :cond_f
    const/4 v9, 0x0

    goto :goto_a

    .line 266
    .end local v5    # "left":Landroid/support/constraint/solver/SolverVariable;
    .end local v6    # "leftTarget":Landroid/support/constraint/solver/SolverVariable;
    .end local v7    # "leftMargin":I
    .end local v10    # "right":Landroid/support/constraint/solver/SolverVariable;
    .end local v11    # "rightMargin":I
    .end local v33    # "previous":Landroid/support/constraint/solver/widgets/ConstraintWidget;
    :cond_10
    const/16 v33, 0x0

    .line 267
    .restart local v33    # "previous":Landroid/support/constraint/solver/widgets/ConstraintWidget;
    const/4 v14, 0x0

    .line 268
    .local v14, "totalWeights":F
    :goto_b
    if-eqz v33, :cond_11

    move-object/from16 v0, v35

    iget-object v4, v0, Landroid/support/constraint/solver/widgets/ConstraintWidget;->mLeft:Landroid/support/constraint/solver/widgets/ConstraintAnchor;

    iget-object v4, v4, Landroid/support/constraint/solver/widgets/ConstraintAnchor;->mTarget:Landroid/support/constraint/solver/widgets/ConstraintAnchor;

    if-eqz v4, :cond_15

    move-object/from16 v0, v35

    iget-object v4, v0, Landroid/support/constraint/solver/widgets/ConstraintWidget;->mLeft:Landroid/support/constraint/solver/widgets/ConstraintAnchor;

    iget-object v4, v4, Landroid/support/constraint/solver/widgets/ConstraintAnchor;->mTarget:Landroid/support/constraint/solver/widgets/ConstraintAnchor;

    iget-object v4, v4, Landroid/support/constraint/solver/widgets/ConstraintAnchor;->mOwner:Landroid/support/constraint/solver/widgets/ConstraintWidget;

    move-object/from16 v0, v33

    if-ne v4, v0, :cond_15

    .line 269
    :cond_11
    move-object/from16 v0, v35

    iget-object v4, v0, Landroid/support/constraint/solver/widgets/ConstraintWidget;->mHorizontalDimensionBehaviour:Landroid/support/constraint/solver/widgets/ConstraintWidget$DimensionBehaviour;

    sget-object v8, Landroid/support/constraint/solver/widgets/ConstraintWidget$DimensionBehaviour;->MATCH_CONSTRAINT:Landroid/support/constraint/solver/widgets/ConstraintWidget$DimensionBehaviour;

    if-eq v4, v8, :cond_14

    .line 270
    move-object/from16 v0, v35

    iget-object v4, v0, Landroid/support/constraint/solver/widgets/ConstraintWidget;->mLeft:Landroid/support/constraint/solver/widgets/ConstraintAnchor;

    invoke-virtual {v4}, Landroid/support/constraint/solver/widgets/ConstraintAnchor;->getMargin()I

    move-result v29

    .line 271
    .restart local v29    # "margin":I
    if-eqz v33, :cond_12

    .line 272
    move-object/from16 v0, v33

    iget-object v4, v0, Landroid/support/constraint/solver/widgets/ConstraintWidget;->mRight:Landroid/support/constraint/solver/widgets/ConstraintAnchor;

    invoke-virtual {v4}, Landroid/support/constraint/solver/widgets/ConstraintAnchor;->getMargin()I

    move-result v4

    add-int v29, v29, v4

    .line 274
    :cond_12
    move-object/from16 v0, v35

    iget-object v4, v0, Landroid/support/constraint/solver/widgets/ConstraintWidget;->mLeft:Landroid/support/constraint/solver/widgets/ConstraintAnchor;

    iget-object v4, v4, Landroid/support/constraint/solver/widgets/ConstraintAnchor;->mSolverVariable:Landroid/support/constraint/solver/SolverVariable;

    move-object/from16 v0, v35

    iget-object v8, v0, Landroid/support/constraint/solver/widgets/ConstraintWidget;->mLeft:Landroid/support/constraint/solver/widgets/ConstraintAnchor;

    iget-object v8, v8, Landroid/support/constraint/solver/widgets/ConstraintAnchor;->mTarget:Landroid/support/constraint/solver/widgets/ConstraintAnchor;

    iget-object v8, v8, Landroid/support/constraint/solver/widgets/ConstraintAnchor;->mSolverVariable:Landroid/support/constraint/solver/SolverVariable;

    move-object/from16 v0, p1

    move/from16 v1, v29

    invoke-virtual {v0, v4, v8, v1}, Landroid/support/constraint/solver/LinearSystem;->addGreaterThan(Landroid/support/constraint/solver/SolverVariable;Landroid/support/constraint/solver/SolverVariable;I)V

    .line 275
    move-object/from16 v0, v35

    iget-object v4, v0, Landroid/support/constraint/solver/widgets/ConstraintWidget;->mRight:Landroid/support/constraint/solver/widgets/ConstraintAnchor;

    invoke-virtual {v4}, Landroid/support/constraint/solver/widgets/ConstraintAnchor;->getMargin()I

    move-result v29

    .line 276
    move-object/from16 v0, v35

    iget-object v4, v0, Landroid/support/constraint/solver/widgets/ConstraintWidget;->mRight:Landroid/support/constraint/solver/widgets/ConstraintAnchor;

    iget-object v4, v4, Landroid/support/constraint/solver/widgets/ConstraintAnchor;->mTarget:Landroid/support/constraint/solver/widgets/ConstraintAnchor;

    iget-object v4, v4, Landroid/support/constraint/solver/widgets/ConstraintAnchor;->mOwner:Landroid/support/constraint/solver/widgets/ConstraintWidget;

    iget-object v4, v4, Landroid/support/constraint/solver/widgets/ConstraintWidget;->mLeft:Landroid/support/constraint/solver/widgets/ConstraintAnchor;

    iget-object v4, v4, Landroid/support/constraint/solver/widgets/ConstraintAnchor;->mTarget:Landroid/support/constraint/solver/widgets/ConstraintAnchor;

    if-eqz v4, :cond_13

    move-object/from16 v0, v35

    iget-object v4, v0, Landroid/support/constraint/solver/widgets/ConstraintWidget;->mRight:Landroid/support/constraint/solver/widgets/ConstraintAnchor;

    iget-object v4, v4, Landroid/support/constraint/solver/widgets/ConstraintAnchor;->mTarget:Landroid/support/constraint/solver/widgets/ConstraintAnchor;

    iget-object v4, v4, Landroid/support/constraint/solver/widgets/ConstraintAnchor;->mOwner:Landroid/support/constraint/solver/widgets/ConstraintWidget;

    iget-object v4, v4, Landroid/support/constraint/solver/widgets/ConstraintWidget;->mLeft:Landroid/support/constraint/solver/widgets/ConstraintAnchor;

    iget-object v4, v4, Landroid/support/constraint/solver/widgets/ConstraintAnchor;->mTarget:Landroid/support/constraint/solver/widgets/ConstraintAnchor;

    iget-object v4, v4, Landroid/support/constraint/solver/widgets/ConstraintAnchor;->mOwner:Landroid/support/constraint/solver/widgets/ConstraintWidget;

    move-object/from16 v0, v35

    if-ne v4, v0, :cond_13

    .line 277
    move-object/from16 v0, v35

    iget-object v4, v0, Landroid/support/constraint/solver/widgets/ConstraintWidget;->mRight:Landroid/support/constraint/solver/widgets/ConstraintAnchor;

    iget-object v4, v4, Landroid/support/constraint/solver/widgets/ConstraintAnchor;->mTarget:Landroid/support/constraint/solver/widgets/ConstraintAnchor;

    iget-object v4, v4, Landroid/support/constraint/solver/widgets/ConstraintAnchor;->mOwner:Landroid/support/constraint/solver/widgets/ConstraintWidget;

    iget-object v4, v4, Landroid/support/constraint/solver/widgets/ConstraintWidget;->mLeft:Landroid/support/constraint/solver/widgets/ConstraintAnchor;

    invoke-virtual {v4}, Landroid/support/constraint/solver/widgets/ConstraintAnchor;->getMargin()I

    move-result v4

    add-int v29, v29, v4

    .line 279
    :cond_13
    move-object/from16 v0, v35

    iget-object v4, v0, Landroid/support/constraint/solver/widgets/ConstraintWidget;->mRight:Landroid/support/constraint/solver/widgets/ConstraintAnchor;

    iget-object v4, v4, Landroid/support/constraint/solver/widgets/ConstraintAnchor;->mSolverVariable:Landroid/support/constraint/solver/SolverVariable;

    move-object/from16 v0, v35

    iget-object v8, v0, Landroid/support/constraint/solver/widgets/ConstraintWidget;->mRight:Landroid/support/constraint/solver/widgets/ConstraintAnchor;

    iget-object v8, v8, Landroid/support/constraint/solver/widgets/ConstraintAnchor;->mTarget:Landroid/support/constraint/solver/widgets/ConstraintAnchor;

    iget-object v8, v8, Landroid/support/constraint/solver/widgets/ConstraintAnchor;->mSolverVariable:Landroid/support/constraint/solver/SolverVariable;

    move/from16 v0, v29

    neg-int v13, v0

    move-object/from16 v0, p1

    invoke-virtual {v0, v4, v8, v13}, Landroid/support/constraint/solver/LinearSystem;->addLowerThan(Landroid/support/constraint/solver/SolverVariable;Landroid/support/constraint/solver/SolverVariable;I)V

    .line 283
    .end local v29    # "margin":I
    :goto_c
    move-object/from16 v33, v35

    .line 284
    move-object/from16 v0, v35

    iget-object v4, v0, Landroid/support/constraint/solver/widgets/ConstraintWidget;->mRight:Landroid/support/constraint/solver/widgets/ConstraintAnchor;

    iget-object v4, v4, Landroid/support/constraint/solver/widgets/ConstraintAnchor;->mTarget:Landroid/support/constraint/solver/widgets/ConstraintAnchor;

    iget-object v0, v4, Landroid/support/constraint/solver/widgets/ConstraintAnchor;->mOwner:Landroid/support/constraint/solver/widgets/ConstraintWidget;

    move-object/from16 v35, v0

    goto/16 :goto_b

    .line 281
    :cond_14
    move-object/from16 v0, v35

    iget v4, v0, Landroid/support/constraint/solver/widgets/ConstraintWidget;->mHorizontalWeight:F

    add-float/2addr v14, v4

    goto :goto_c

    .line 286
    :cond_15
    const/4 v4, 0x1

    move/from16 v0, v32

    if-ne v0, v4, :cond_16

    .line 287
    move-object/from16 v0, p0

    iget-object v4, v0, Landroid/support/constraint/solver/widgets/ConstraintWidgetContainer;->mMatchConstraintsChainedWidgets:[Landroid/support/constraint/solver/widgets/ConstraintWidget;

    const/4 v8, 0x0

    aget-object v34, v4, v8

    .line 288
    .local v34, "w":Landroid/support/constraint/solver/widgets/ConstraintWidget;
    move-object/from16 v0, v34

    iget-object v4, v0, Landroid/support/constraint/solver/widgets/ConstraintWidget;->mLeft:Landroid/support/constraint/solver/widgets/ConstraintAnchor;

    iget-object v4, v4, Landroid/support/constraint/solver/widgets/ConstraintAnchor;->mSolverVariable:Landroid/support/constraint/solver/SolverVariable;

    move-object/from16 v0, v34

    iget-object v8, v0, Landroid/support/constraint/solver/widgets/ConstraintWidget;->mLeft:Landroid/support/constraint/solver/widgets/ConstraintAnchor;

    iget-object v8, v8, Landroid/support/constraint/solver/widgets/ConstraintAnchor;->mTarget:Landroid/support/constraint/solver/widgets/ConstraintAnchor;

    iget-object v8, v8, Landroid/support/constraint/solver/widgets/ConstraintAnchor;->mSolverVariable:Landroid/support/constraint/solver/SolverVariable;

    move-object/from16 v0, v34

    iget-object v13, v0, Landroid/support/constraint/solver/widgets/ConstraintWidget;->mLeft:Landroid/support/constraint/solver/widgets/ConstraintAnchor;

    invoke-virtual {v13}, Landroid/support/constraint/solver/widgets/ConstraintAnchor;->getMargin()I

    move-result v13

    move-object/from16 v0, p1

    invoke-virtual {v0, v4, v8, v13}, Landroid/support/constraint/solver/LinearSystem;->addEquality(Landroid/support/constraint/solver/SolverVariable;Landroid/support/constraint/solver/SolverVariable;I)V

    .line 289
    move-object/from16 v0, v34

    iget-object v4, v0, Landroid/support/constraint/solver/widgets/ConstraintWidget;->mRight:Landroid/support/constraint/solver/widgets/ConstraintAnchor;

    iget-object v4, v4, Landroid/support/constraint/solver/widgets/ConstraintAnchor;->mSolverVariable:Landroid/support/constraint/solver/SolverVariable;

    move-object/from16 v0, v34

    iget-object v8, v0, Landroid/support/constraint/solver/widgets/ConstraintWidget;->mRight:Landroid/support/constraint/solver/widgets/ConstraintAnchor;

    iget-object v8, v8, Landroid/support/constraint/solver/widgets/ConstraintAnchor;->mTarget:Landroid/support/constraint/solver/widgets/ConstraintAnchor;

    iget-object v8, v8, Landroid/support/constraint/solver/widgets/ConstraintAnchor;->mSolverVariable:Landroid/support/constraint/solver/SolverVariable;

    move-object/from16 v0, v34

    iget-object v13, v0, Landroid/support/constraint/solver/widgets/ConstraintWidget;->mRight:Landroid/support/constraint/solver/widgets/ConstraintAnchor;

    invoke-virtual {v13}, Landroid/support/constraint/solver/widgets/ConstraintAnchor;->getMargin()I

    move-result v13

    mul-int/lit8 v13, v13, -0x1

    move-object/from16 v0, p1

    invoke-virtual {v0, v4, v8, v13}, Landroid/support/constraint/solver/LinearSystem;->addEquality(Landroid/support/constraint/solver/SolverVariable;Landroid/support/constraint/solver/SolverVariable;I)V

    .line 290
    move-object/from16 v0, v34

    iget-object v4, v0, Landroid/support/constraint/solver/widgets/ConstraintWidget;->mRight:Landroid/support/constraint/solver/widgets/ConstraintAnchor;

    iget-object v4, v4, Landroid/support/constraint/solver/widgets/ConstraintAnchor;->mSolverVariable:Landroid/support/constraint/solver/SolverVariable;

    move-object/from16 v0, v34

    iget-object v8, v0, Landroid/support/constraint/solver/widgets/ConstraintWidget;->mRight:Landroid/support/constraint/solver/widgets/ConstraintAnchor;

    iget-object v8, v8, Landroid/support/constraint/solver/widgets/ConstraintAnchor;->mTarget:Landroid/support/constraint/solver/widgets/ConstraintAnchor;

    iget-object v8, v8, Landroid/support/constraint/solver/widgets/ConstraintAnchor;->mSolverVariable:Landroid/support/constraint/solver/SolverVariable;

    const/4 v13, 0x0

    move-object/from16 v0, p1

    invoke-virtual {v0, v4, v8, v13}, Landroid/support/constraint/solver/LinearSystem;->addLowerThan(Landroid/support/constraint/solver/SolverVariable;Landroid/support/constraint/solver/SolverVariable;I)V

    goto/16 :goto_2

    .line 292
    .end local v34    # "w":Landroid/support/constraint/solver/widgets/ConstraintWidget;
    :cond_16
    const/16 v28, 0x0

    .local v28, "j":I
    :goto_d
    add-int/lit8 v4, v32, -0x1

    move/from16 v0, v28

    if-ge v0, v4, :cond_0

    .line 293
    move-object/from16 v0, p0

    iget-object v4, v0, Landroid/support/constraint/solver/widgets/ConstraintWidgetContainer;->mMatchConstraintsChainedWidgets:[Landroid/support/constraint/solver/widgets/ConstraintWidget;

    aget-object v25, v4, v28

    .line 294
    .local v25, "current":Landroid/support/constraint/solver/widgets/ConstraintWidget;
    move-object/from16 v0, p0

    iget-object v4, v0, Landroid/support/constraint/solver/widgets/ConstraintWidgetContainer;->mMatchConstraintsChainedWidgets:[Landroid/support/constraint/solver/widgets/ConstraintWidget;

    add-int/lit8 v8, v28, 0x1

    aget-object v31, v4, v8

    .line 295
    .local v31, "nextWidget":Landroid/support/constraint/solver/widgets/ConstraintWidget;
    move-object/from16 v0, v25

    iget-object v4, v0, Landroid/support/constraint/solver/widgets/ConstraintWidget;->mLeft:Landroid/support/constraint/solver/widgets/ConstraintAnchor;

    iget-object v5, v4, Landroid/support/constraint/solver/widgets/ConstraintAnchor;->mSolverVariable:Landroid/support/constraint/solver/SolverVariable;

    .line 296
    .restart local v5    # "left":Landroid/support/constraint/solver/SolverVariable;
    move-object/from16 v0, v25

    iget-object v4, v0, Landroid/support/constraint/solver/widgets/ConstraintWidget;->mRight:Landroid/support/constraint/solver/widgets/ConstraintAnchor;

    iget-object v10, v4, Landroid/support/constraint/solver/widgets/ConstraintAnchor;->mSolverVariable:Landroid/support/constraint/solver/SolverVariable;

    .line 297
    .restart local v10    # "right":Landroid/support/constraint/solver/SolverVariable;
    move-object/from16 v0, v31

    iget-object v4, v0, Landroid/support/constraint/solver/widgets/ConstraintWidget;->mLeft:Landroid/support/constraint/solver/widgets/ConstraintAnchor;

    iget-object v0, v4, Landroid/support/constraint/solver/widgets/ConstraintAnchor;->mSolverVariable:Landroid/support/constraint/solver/SolverVariable;

    move-object/from16 v20, v0

    .line 298
    .local v20, "nextLeft":Landroid/support/constraint/solver/SolverVariable;
    move-object/from16 v0, v31

    iget-object v4, v0, Landroid/support/constraint/solver/widgets/ConstraintWidget;->mRight:Landroid/support/constraint/solver/widgets/ConstraintAnchor;

    iget-object v0, v4, Landroid/support/constraint/solver/widgets/ConstraintAnchor;->mSolverVariable:Landroid/support/constraint/solver/SolverVariable;

    move-object/from16 v22, v0

    .line 299
    .local v22, "nextRight":Landroid/support/constraint/solver/SolverVariable;
    move-object/from16 v0, v25

    iget-object v4, v0, Landroid/support/constraint/solver/widgets/ConstraintWidget;->mLeft:Landroid/support/constraint/solver/widgets/ConstraintAnchor;

    invoke-virtual {v4}, Landroid/support/constraint/solver/widgets/ConstraintAnchor;->getMargin()I

    move-result v29

    .line 300
    .restart local v29    # "margin":I
    move-object/from16 v0, v25

    iget-object v4, v0, Landroid/support/constraint/solver/widgets/ConstraintWidget;->mLeft:Landroid/support/constraint/solver/widgets/ConstraintAnchor;

    iget-object v4, v4, Landroid/support/constraint/solver/widgets/ConstraintAnchor;->mTarget:Landroid/support/constraint/solver/widgets/ConstraintAnchor;

    if-eqz v4, :cond_17

    move-object/from16 v0, v25

    iget-object v4, v0, Landroid/support/constraint/solver/widgets/ConstraintWidget;->mLeft:Landroid/support/constraint/solver/widgets/ConstraintAnchor;

    iget-object v4, v4, Landroid/support/constraint/solver/widgets/ConstraintAnchor;->mTarget:Landroid/support/constraint/solver/widgets/ConstraintAnchor;

    iget-object v4, v4, Landroid/support/constraint/solver/widgets/ConstraintAnchor;->mOwner:Landroid/support/constraint/solver/widgets/ConstraintWidget;

    iget-object v4, v4, Landroid/support/constraint/solver/widgets/ConstraintWidget;->mRight:Landroid/support/constraint/solver/widgets/ConstraintAnchor;

    iget-object v4, v4, Landroid/support/constraint/solver/widgets/ConstraintAnchor;->mTarget:Landroid/support/constraint/solver/widgets/ConstraintAnchor;

    if-eqz v4, :cond_17

    move-object/from16 v0, v25

    iget-object v4, v0, Landroid/support/constraint/solver/widgets/ConstraintWidget;->mLeft:Landroid/support/constraint/solver/widgets/ConstraintAnchor;

    iget-object v4, v4, Landroid/support/constraint/solver/widgets/ConstraintAnchor;->mTarget:Landroid/support/constraint/solver/widgets/ConstraintAnchor;

    iget-object v4, v4, Landroid/support/constraint/solver/widgets/ConstraintAnchor;->mOwner:Landroid/support/constraint/solver/widgets/ConstraintWidget;

    iget-object v4, v4, Landroid/support/constraint/solver/widgets/ConstraintWidget;->mRight:Landroid/support/constraint/solver/widgets/ConstraintAnchor;

    iget-object v4, v4, Landroid/support/constraint/solver/widgets/ConstraintAnchor;->mTarget:Landroid/support/constraint/solver/widgets/ConstraintAnchor;

    iget-object v4, v4, Landroid/support/constraint/solver/widgets/ConstraintAnchor;->mOwner:Landroid/support/constraint/solver/widgets/ConstraintWidget;

    move-object/from16 v0, v25

    if-ne v4, v0, :cond_17

    .line 302
    move-object/from16 v0, v25

    iget-object v4, v0, Landroid/support/constraint/solver/widgets/ConstraintWidget;->mLeft:Landroid/support/constraint/solver/widgets/ConstraintAnchor;

    iget-object v4, v4, Landroid/support/constraint/solver/widgets/ConstraintAnchor;->mTarget:Landroid/support/constraint/solver/widgets/ConstraintAnchor;

    iget-object v4, v4, Landroid/support/constraint/solver/widgets/ConstraintAnchor;->mOwner:Landroid/support/constraint/solver/widgets/ConstraintWidget;

    iget-object v4, v4, Landroid/support/constraint/solver/widgets/ConstraintWidget;->mRight:Landroid/support/constraint/solver/widgets/ConstraintAnchor;

    invoke-virtual {v4}, Landroid/support/constraint/solver/widgets/ConstraintAnchor;->getMargin()I

    move-result v4

    add-int v29, v29, v4

    .line 304
    :cond_17
    move-object/from16 v0, v25

    iget-object v4, v0, Landroid/support/constraint/solver/widgets/ConstraintWidget;->mLeft:Landroid/support/constraint/solver/widgets/ConstraintAnchor;

    iget-object v4, v4, Landroid/support/constraint/solver/widgets/ConstraintAnchor;->mTarget:Landroid/support/constraint/solver/widgets/ConstraintAnchor;

    iget-object v4, v4, Landroid/support/constraint/solver/widgets/ConstraintAnchor;->mSolverVariable:Landroid/support/constraint/solver/SolverVariable;

    move-object/from16 v0, p1

    move/from16 v1, v29

    invoke-virtual {v0, v5, v4, v1}, Landroid/support/constraint/solver/LinearSystem;->addGreaterThan(Landroid/support/constraint/solver/SolverVariable;Landroid/support/constraint/solver/SolverVariable;I)V

    .line 305
    move-object/from16 v0, v25

    iget-object v4, v0, Landroid/support/constraint/solver/widgets/ConstraintWidget;->mRight:Landroid/support/constraint/solver/widgets/ConstraintAnchor;

    invoke-virtual {v4}, Landroid/support/constraint/solver/widgets/ConstraintAnchor;->getMargin()I

    move-result v29

    .line 306
    move-object/from16 v0, v25

    iget-object v4, v0, Landroid/support/constraint/solver/widgets/ConstraintWidget;->mRight:Landroid/support/constraint/solver/widgets/ConstraintAnchor;

    iget-object v4, v4, Landroid/support/constraint/solver/widgets/ConstraintAnchor;->mTarget:Landroid/support/constraint/solver/widgets/ConstraintAnchor;

    if-eqz v4, :cond_18

    move-object/from16 v0, v25

    iget-object v4, v0, Landroid/support/constraint/solver/widgets/ConstraintWidget;->mRight:Landroid/support/constraint/solver/widgets/ConstraintAnchor;

    iget-object v4, v4, Landroid/support/constraint/solver/widgets/ConstraintAnchor;->mTarget:Landroid/support/constraint/solver/widgets/ConstraintAnchor;

    iget-object v4, v4, Landroid/support/constraint/solver/widgets/ConstraintAnchor;->mOwner:Landroid/support/constraint/solver/widgets/ConstraintWidget;

    iget-object v4, v4, Landroid/support/constraint/solver/widgets/ConstraintWidget;->mLeft:Landroid/support/constraint/solver/widgets/ConstraintAnchor;

    iget-object v4, v4, Landroid/support/constraint/solver/widgets/ConstraintAnchor;->mTarget:Landroid/support/constraint/solver/widgets/ConstraintAnchor;

    if-eqz v4, :cond_18

    move-object/from16 v0, v25

    iget-object v4, v0, Landroid/support/constraint/solver/widgets/ConstraintWidget;->mRight:Landroid/support/constraint/solver/widgets/ConstraintAnchor;

    iget-object v4, v4, Landroid/support/constraint/solver/widgets/ConstraintAnchor;->mTarget:Landroid/support/constraint/solver/widgets/ConstraintAnchor;

    iget-object v4, v4, Landroid/support/constraint/solver/widgets/ConstraintAnchor;->mOwner:Landroid/support/constraint/solver/widgets/ConstraintWidget;

    iget-object v4, v4, Landroid/support/constraint/solver/widgets/ConstraintWidget;->mLeft:Landroid/support/constraint/solver/widgets/ConstraintAnchor;

    iget-object v4, v4, Landroid/support/constraint/solver/widgets/ConstraintAnchor;->mTarget:Landroid/support/constraint/solver/widgets/ConstraintAnchor;

    iget-object v4, v4, Landroid/support/constraint/solver/widgets/ConstraintAnchor;->mOwner:Landroid/support/constraint/solver/widgets/ConstraintWidget;

    move-object/from16 v0, v25

    if-ne v4, v0, :cond_18

    .line 308
    move-object/from16 v0, v25

    iget-object v4, v0, Landroid/support/constraint/solver/widgets/ConstraintWidget;->mRight:Landroid/support/constraint/solver/widgets/ConstraintAnchor;

    iget-object v4, v4, Landroid/support/constraint/solver/widgets/ConstraintAnchor;->mTarget:Landroid/support/constraint/solver/widgets/ConstraintAnchor;

    iget-object v4, v4, Landroid/support/constraint/solver/widgets/ConstraintAnchor;->mOwner:Landroid/support/constraint/solver/widgets/ConstraintWidget;

    iget-object v4, v4, Landroid/support/constraint/solver/widgets/ConstraintWidget;->mLeft:Landroid/support/constraint/solver/widgets/ConstraintAnchor;

    invoke-virtual {v4}, Landroid/support/constraint/solver/widgets/ConstraintAnchor;->getMargin()I

    move-result v4

    add-int v29, v29, v4

    .line 310
    :cond_18
    move-object/from16 v0, v25

    iget-object v4, v0, Landroid/support/constraint/solver/widgets/ConstraintWidget;->mRight:Landroid/support/constraint/solver/widgets/ConstraintAnchor;

    iget-object v4, v4, Landroid/support/constraint/solver/widgets/ConstraintAnchor;->mTarget:Landroid/support/constraint/solver/widgets/ConstraintAnchor;

    iget-object v4, v4, Landroid/support/constraint/solver/widgets/ConstraintAnchor;->mSolverVariable:Landroid/support/constraint/solver/SolverVariable;

    move/from16 v0, v29

    neg-int v8, v0

    move-object/from16 v0, p1

    invoke-virtual {v0, v10, v4, v8}, Landroid/support/constraint/solver/LinearSystem;->addLowerThan(Landroid/support/constraint/solver/SolverVariable;Landroid/support/constraint/solver/SolverVariable;I)V

    .line 311
    add-int/lit8 v4, v28, 0x1

    add-int/lit8 v8, v32, -0x1

    if-ne v4, v8, :cond_1b

    .line 313
    move-object/from16 v0, v31

    iget-object v4, v0, Landroid/support/constraint/solver/widgets/ConstraintWidget;->mLeft:Landroid/support/constraint/solver/widgets/ConstraintAnchor;

    invoke-virtual {v4}, Landroid/support/constraint/solver/widgets/ConstraintAnchor;->getMargin()I

    move-result v29

    .line 314
    move-object/from16 v0, v31

    iget-object v4, v0, Landroid/support/constraint/solver/widgets/ConstraintWidget;->mLeft:Landroid/support/constraint/solver/widgets/ConstraintAnchor;

    iget-object v4, v4, Landroid/support/constraint/solver/widgets/ConstraintAnchor;->mTarget:Landroid/support/constraint/solver/widgets/ConstraintAnchor;

    if-eqz v4, :cond_19

    move-object/from16 v0, v31

    iget-object v4, v0, Landroid/support/constraint/solver/widgets/ConstraintWidget;->mLeft:Landroid/support/constraint/solver/widgets/ConstraintAnchor;

    iget-object v4, v4, Landroid/support/constraint/solver/widgets/ConstraintAnchor;->mTarget:Landroid/support/constraint/solver/widgets/ConstraintAnchor;

    iget-object v4, v4, Landroid/support/constraint/solver/widgets/ConstraintAnchor;->mOwner:Landroid/support/constraint/solver/widgets/ConstraintWidget;

    iget-object v4, v4, Landroid/support/constraint/solver/widgets/ConstraintWidget;->mRight:Landroid/support/constraint/solver/widgets/ConstraintAnchor;

    iget-object v4, v4, Landroid/support/constraint/solver/widgets/ConstraintAnchor;->mTarget:Landroid/support/constraint/solver/widgets/ConstraintAnchor;

    if-eqz v4, :cond_19

    move-object/from16 v0, v31

    iget-object v4, v0, Landroid/support/constraint/solver/widgets/ConstraintWidget;->mLeft:Landroid/support/constraint/solver/widgets/ConstraintAnchor;

    iget-object v4, v4, Landroid/support/constraint/solver/widgets/ConstraintAnchor;->mTarget:Landroid/support/constraint/solver/widgets/ConstraintAnchor;

    iget-object v4, v4, Landroid/support/constraint/solver/widgets/ConstraintAnchor;->mOwner:Landroid/support/constraint/solver/widgets/ConstraintWidget;

    iget-object v4, v4, Landroid/support/constraint/solver/widgets/ConstraintWidget;->mRight:Landroid/support/constraint/solver/widgets/ConstraintAnchor;

    iget-object v4, v4, Landroid/support/constraint/solver/widgets/ConstraintAnchor;->mTarget:Landroid/support/constraint/solver/widgets/ConstraintAnchor;

    iget-object v4, v4, Landroid/support/constraint/solver/widgets/ConstraintAnchor;->mOwner:Landroid/support/constraint/solver/widgets/ConstraintWidget;

    move-object/from16 v0, v31

    if-ne v4, v0, :cond_19

    .line 316
    move-object/from16 v0, v31

    iget-object v4, v0, Landroid/support/constraint/solver/widgets/ConstraintWidget;->mLeft:Landroid/support/constraint/solver/widgets/ConstraintAnchor;

    iget-object v4, v4, Landroid/support/constraint/solver/widgets/ConstraintAnchor;->mTarget:Landroid/support/constraint/solver/widgets/ConstraintAnchor;

    iget-object v4, v4, Landroid/support/constraint/solver/widgets/ConstraintAnchor;->mOwner:Landroid/support/constraint/solver/widgets/ConstraintWidget;

    iget-object v4, v4, Landroid/support/constraint/solver/widgets/ConstraintWidget;->mRight:Landroid/support/constraint/solver/widgets/ConstraintAnchor;

    invoke-virtual {v4}, Landroid/support/constraint/solver/widgets/ConstraintAnchor;->getMargin()I

    move-result v4

    add-int v29, v29, v4

    .line 318
    :cond_19
    move-object/from16 v0, v31

    iget-object v4, v0, Landroid/support/constraint/solver/widgets/ConstraintWidget;->mLeft:Landroid/support/constraint/solver/widgets/ConstraintAnchor;

    iget-object v4, v4, Landroid/support/constraint/solver/widgets/ConstraintAnchor;->mTarget:Landroid/support/constraint/solver/widgets/ConstraintAnchor;

    iget-object v4, v4, Landroid/support/constraint/solver/widgets/ConstraintAnchor;->mSolverVariable:Landroid/support/constraint/solver/SolverVariable;

    move-object/from16 v0, p1

    move-object/from16 v1, v20

    move/from16 v2, v29

    invoke-virtual {v0, v1, v4, v2}, Landroid/support/constraint/solver/LinearSystem;->addGreaterThan(Landroid/support/constraint/solver/SolverVariable;Landroid/support/constraint/solver/SolverVariable;I)V

    .line 319
    move-object/from16 v0, v31

    iget-object v4, v0, Landroid/support/constraint/solver/widgets/ConstraintWidget;->mRight:Landroid/support/constraint/solver/widgets/ConstraintAnchor;

    invoke-virtual {v4}, Landroid/support/constraint/solver/widgets/ConstraintAnchor;->getMargin()I

    move-result v29

    .line 320
    move-object/from16 v0, v31

    iget-object v4, v0, Landroid/support/constraint/solver/widgets/ConstraintWidget;->mRight:Landroid/support/constraint/solver/widgets/ConstraintAnchor;

    iget-object v4, v4, Landroid/support/constraint/solver/widgets/ConstraintAnchor;->mTarget:Landroid/support/constraint/solver/widgets/ConstraintAnchor;

    if-eqz v4, :cond_1a

    move-object/from16 v0, v31

    iget-object v4, v0, Landroid/support/constraint/solver/widgets/ConstraintWidget;->mRight:Landroid/support/constraint/solver/widgets/ConstraintAnchor;

    iget-object v4, v4, Landroid/support/constraint/solver/widgets/ConstraintAnchor;->mTarget:Landroid/support/constraint/solver/widgets/ConstraintAnchor;

    iget-object v4, v4, Landroid/support/constraint/solver/widgets/ConstraintAnchor;->mOwner:Landroid/support/constraint/solver/widgets/ConstraintWidget;

    iget-object v4, v4, Landroid/support/constraint/solver/widgets/ConstraintWidget;->mLeft:Landroid/support/constraint/solver/widgets/ConstraintAnchor;

    iget-object v4, v4, Landroid/support/constraint/solver/widgets/ConstraintAnchor;->mTarget:Landroid/support/constraint/solver/widgets/ConstraintAnchor;

    if-eqz v4, :cond_1a

    move-object/from16 v0, v31

    iget-object v4, v0, Landroid/support/constraint/solver/widgets/ConstraintWidget;->mRight:Landroid/support/constraint/solver/widgets/ConstraintAnchor;

    iget-object v4, v4, Landroid/support/constraint/solver/widgets/ConstraintAnchor;->mTarget:Landroid/support/constraint/solver/widgets/ConstraintAnchor;

    iget-object v4, v4, Landroid/support/constraint/solver/widgets/ConstraintAnchor;->mOwner:Landroid/support/constraint/solver/widgets/ConstraintWidget;

    iget-object v4, v4, Landroid/support/constraint/solver/widgets/ConstraintWidget;->mLeft:Landroid/support/constraint/solver/widgets/ConstraintAnchor;

    iget-object v4, v4, Landroid/support/constraint/solver/widgets/ConstraintAnchor;->mTarget:Landroid/support/constraint/solver/widgets/ConstraintAnchor;

    iget-object v4, v4, Landroid/support/constraint/solver/widgets/ConstraintAnchor;->mOwner:Landroid/support/constraint/solver/widgets/ConstraintWidget;

    move-object/from16 v0, v31

    if-ne v4, v0, :cond_1a

    .line 322
    move-object/from16 v0, v31

    iget-object v4, v0, Landroid/support/constraint/solver/widgets/ConstraintWidget;->mRight:Landroid/support/constraint/solver/widgets/ConstraintAnchor;

    iget-object v4, v4, Landroid/support/constraint/solver/widgets/ConstraintAnchor;->mTarget:Landroid/support/constraint/solver/widgets/ConstraintAnchor;

    iget-object v4, v4, Landroid/support/constraint/solver/widgets/ConstraintAnchor;->mOwner:Landroid/support/constraint/solver/widgets/ConstraintWidget;

    iget-object v4, v4, Landroid/support/constraint/solver/widgets/ConstraintWidget;->mLeft:Landroid/support/constraint/solver/widgets/ConstraintAnchor;

    invoke-virtual {v4}, Landroid/support/constraint/solver/widgets/ConstraintAnchor;->getMargin()I

    move-result v4

    add-int v29, v29, v4

    .line 324
    :cond_1a
    move-object/from16 v0, v31

    iget-object v4, v0, Landroid/support/constraint/solver/widgets/ConstraintWidget;->mRight:Landroid/support/constraint/solver/widgets/ConstraintAnchor;

    iget-object v4, v4, Landroid/support/constraint/solver/widgets/ConstraintAnchor;->mTarget:Landroid/support/constraint/solver/widgets/ConstraintAnchor;

    iget-object v4, v4, Landroid/support/constraint/solver/widgets/ConstraintAnchor;->mSolverVariable:Landroid/support/constraint/solver/SolverVariable;

    move/from16 v0, v29

    neg-int v8, v0

    move-object/from16 v0, p1

    move-object/from16 v1, v22

    invoke-virtual {v0, v1, v4, v8}, Landroid/support/constraint/solver/LinearSystem;->addLowerThan(Landroid/support/constraint/solver/SolverVariable;Landroid/support/constraint/solver/SolverVariable;I)V

    .line 327
    :cond_1b
    invoke-virtual/range {p1 .. p1}, Landroid/support/constraint/solver/LinearSystem;->createRow()Landroid/support/constraint/solver/ArrayRow;

    move-result-object v12

    .line 328
    .local v12, "row":Landroid/support/constraint/solver/ArrayRow;
    move-object/from16 v0, v25

    iget v13, v0, Landroid/support/constraint/solver/widgets/ConstraintWidget;->mHorizontalWeight:F

    move-object/from16 v0, v31

    iget v15, v0, Landroid/support/constraint/solver/widgets/ConstraintWidget;->mHorizontalWeight:F

    move-object/from16 v0, v25

    iget-object v4, v0, Landroid/support/constraint/solver/widgets/ConstraintWidget;->mLeft:Landroid/support/constraint/solver/widgets/ConstraintAnchor;

    .line 330
    invoke-virtual {v4}, Landroid/support/constraint/solver/widgets/ConstraintAnchor;->getMargin()I

    move-result v17

    move-object/from16 v0, v25

    iget-object v4, v0, Landroid/support/constraint/solver/widgets/ConstraintWidget;->mRight:Landroid/support/constraint/solver/widgets/ConstraintAnchor;

    .line 331
    invoke-virtual {v4}, Landroid/support/constraint/solver/widgets/ConstraintAnchor;->getMargin()I

    move-result v19

    move-object/from16 v0, v31

    iget-object v4, v0, Landroid/support/constraint/solver/widgets/ConstraintWidget;->mLeft:Landroid/support/constraint/solver/widgets/ConstraintAnchor;

    .line 332
    invoke-virtual {v4}, Landroid/support/constraint/solver/widgets/ConstraintAnchor;->getMargin()I

    move-result v21

    move-object/from16 v0, v31

    iget-object v4, v0, Landroid/support/constraint/solver/widgets/ConstraintWidget;->mRight:Landroid/support/constraint/solver/widgets/ConstraintAnchor;

    .line 333
    invoke-virtual {v4}, Landroid/support/constraint/solver/widgets/ConstraintAnchor;->getMargin()I

    move-result v23

    move-object/from16 v16, v5

    move-object/from16 v18, v10

    .line 328
    invoke-virtual/range {v12 .. v23}, Landroid/support/constraint/solver/ArrayRow;->createRowEqualDimension(FFFLandroid/support/constraint/solver/SolverVariable;ILandroid/support/constraint/solver/SolverVariable;ILandroid/support/constraint/solver/SolverVariable;ILandroid/support/constraint/solver/SolverVariable;I)Landroid/support/constraint/solver/ArrayRow;

    .line 334
    move-object/from16 v0, p1

    invoke-virtual {v0, v12}, Landroid/support/constraint/solver/LinearSystem;->addConstraint(Landroid/support/constraint/solver/ArrayRow;)V

    .line 292
    add-int/lit8 v28, v28, 0x1

    goto/16 :goto_d

    .line 340
    .end local v5    # "left":Landroid/support/constraint/solver/SolverVariable;
    .end local v10    # "right":Landroid/support/constraint/solver/SolverVariable;
    .end local v12    # "row":Landroid/support/constraint/solver/ArrayRow;
    .end local v14    # "totalWeights":F
    .end local v20    # "nextLeft":Landroid/support/constraint/solver/SolverVariable;
    .end local v22    # "nextRight":Landroid/support/constraint/solver/SolverVariable;
    .end local v24    # "chainPacked":Z
    .end local v25    # "current":Landroid/support/constraint/solver/widgets/ConstraintWidget;
    .end local v26    # "first":Landroid/support/constraint/solver/widgets/ConstraintWidget;
    .end local v28    # "j":I
    .end local v29    # "margin":I
    .end local v31    # "nextWidget":Landroid/support/constraint/solver/widgets/ConstraintWidget;
    .end local v32    # "numMatchConstraints":I
    .end local v33    # "previous":Landroid/support/constraint/solver/widgets/ConstraintWidget;
    .end local v35    # "widget":Landroid/support/constraint/solver/widgets/ConstraintWidget;
    :cond_1c
    return-void
.end method

.method private applyVerticalChain(Landroid/support/constraint/solver/LinearSystem;)V
    .locals 37
    .param p1, "system"    # Landroid/support/constraint/solver/LinearSystem;

    .prologue
    .line 436
    const/16 v27, 0x0

    .local v27, "i":I
    :goto_0
    move-object/from16 v0, p0

    iget v4, v0, Landroid/support/constraint/solver/widgets/ConstraintWidgetContainer;->mVerticalChainsSize:I

    move/from16 v0, v27

    if-ge v0, v4, :cond_1c

    .line 437
    move-object/from16 v0, p0

    iget-object v4, v0, Landroid/support/constraint/solver/widgets/ConstraintWidgetContainer;->mVerticalChainsArray:[Landroid/support/constraint/solver/widgets/ConstraintWidget;

    aget-object v26, v4, v27

    .line 438
    .local v26, "first":Landroid/support/constraint/solver/widgets/ConstraintWidget;
    move-object/from16 v0, p0

    iget-object v4, v0, Landroid/support/constraint/solver/widgets/ConstraintWidgetContainer;->mVerticalChainsArray:[Landroid/support/constraint/solver/widgets/ConstraintWidget;

    aget-object v4, v4, v27

    const/4 v8, 0x1

    move-object/from16 v0, p0

    invoke-direct {v0, v4, v8}, Landroid/support/constraint/solver/widgets/ConstraintWidgetContainer;->countMatchConstraintsChainedWidgets(Landroid/support/constraint/solver/widgets/ConstraintWidget;I)I

    move-result v33

    .line 440
    .local v33, "numMatchConstraints":I
    move-object/from16 v0, v26

    iget-boolean v4, v0, Landroid/support/constraint/solver/widgets/ConstraintWidget;->mVerticalChainPacked:Z

    if-eqz v4, :cond_1

    if-nez v33, :cond_1

    const/16 v24, 0x1

    .line 441
    .local v24, "chainPacked":Z
    :goto_1
    move-object/from16 v36, v26

    .line 442
    .local v36, "widget":Landroid/support/constraint/solver/widgets/ConstraintWidget;
    move-object/from16 v0, v36

    iget-boolean v4, v0, Landroid/support/constraint/solver/widgets/ConstraintWidget;->mVerticalChainFixedPosition:Z

    if-eqz v4, :cond_2

    if-nez v24, :cond_2

    .line 443
    move-object/from16 v0, p0

    move-object/from16 v1, p1

    move/from16 v2, v33

    move-object/from16 v3, v36

    invoke-direct {v0, v1, v2, v3}, Landroid/support/constraint/solver/widgets/ConstraintWidgetContainer;->applyDirectResolutionVerticalChain(Landroid/support/constraint/solver/LinearSystem;ILandroid/support/constraint/solver/widgets/ConstraintWidget;)V

    .line 436
    :cond_0
    :goto_2
    add-int/lit8 v27, v27, 0x1

    goto :goto_0

    .line 440
    .end local v24    # "chainPacked":Z
    .end local v36    # "widget":Landroid/support/constraint/solver/widgets/ConstraintWidget;
    :cond_1
    const/16 v24, 0x0

    goto :goto_1

    .line 445
    .restart local v24    # "chainPacked":Z
    .restart local v36    # "widget":Landroid/support/constraint/solver/widgets/ConstraintWidget;
    :cond_2
    if-nez v33, :cond_10

    .line 446
    const/16 v34, 0x0

    .line 447
    .local v34, "previous":Landroid/support/constraint/solver/widgets/ConstraintWidget;
    :goto_3
    if-eqz v34, :cond_3

    move-object/from16 v0, v36

    iget-object v4, v0, Landroid/support/constraint/solver/widgets/ConstraintWidget;->mTop:Landroid/support/constraint/solver/widgets/ConstraintAnchor;

    iget-object v4, v4, Landroid/support/constraint/solver/widgets/ConstraintAnchor;->mTarget:Landroid/support/constraint/solver/widgets/ConstraintAnchor;

    if-eqz v4, :cond_d

    move-object/from16 v0, v36

    iget-object v4, v0, Landroid/support/constraint/solver/widgets/ConstraintWidget;->mTop:Landroid/support/constraint/solver/widgets/ConstraintAnchor;

    iget-object v4, v4, Landroid/support/constraint/solver/widgets/ConstraintAnchor;->mTarget:Landroid/support/constraint/solver/widgets/ConstraintAnchor;

    iget-object v4, v4, Landroid/support/constraint/solver/widgets/ConstraintAnchor;->mOwner:Landroid/support/constraint/solver/widgets/ConstraintWidget;

    move-object/from16 v0, v34

    if-ne v4, v0, :cond_d

    .line 449
    :cond_3
    move-object/from16 v0, v36

    iget-object v4, v0, Landroid/support/constraint/solver/widgets/ConstraintWidget;->mTop:Landroid/support/constraint/solver/widgets/ConstraintAnchor;

    invoke-virtual {v4}, Landroid/support/constraint/solver/widgets/ConstraintAnchor;->getMargin()I

    move-result v7

    .line 450
    .local v7, "topMargin":I
    move-object/from16 v0, v36

    iget-object v4, v0, Landroid/support/constraint/solver/widgets/ConstraintWidget;->mBottom:Landroid/support/constraint/solver/widgets/ConstraintAnchor;

    invoke-virtual {v4}, Landroid/support/constraint/solver/widgets/ConstraintAnchor;->getMargin()I

    move-result v11

    .line 451
    .local v11, "bottomMargin":I
    move-object/from16 v0, v36

    iget-object v4, v0, Landroid/support/constraint/solver/widgets/ConstraintWidget;->mTop:Landroid/support/constraint/solver/widgets/ConstraintAnchor;

    iget-object v5, v4, Landroid/support/constraint/solver/widgets/ConstraintAnchor;->mSolverVariable:Landroid/support/constraint/solver/SolverVariable;

    .line 452
    .local v5, "top":Landroid/support/constraint/solver/SolverVariable;
    move-object/from16 v0, v36

    iget-object v4, v0, Landroid/support/constraint/solver/widgets/ConstraintWidget;->mTop:Landroid/support/constraint/solver/widgets/ConstraintAnchor;

    iget-object v4, v4, Landroid/support/constraint/solver/widgets/ConstraintAnchor;->mTarget:Landroid/support/constraint/solver/widgets/ConstraintAnchor;

    if-eqz v4, :cond_8

    move-object/from16 v0, v36

    iget-object v4, v0, Landroid/support/constraint/solver/widgets/ConstraintWidget;->mTop:Landroid/support/constraint/solver/widgets/ConstraintAnchor;

    iget-object v4, v4, Landroid/support/constraint/solver/widgets/ConstraintAnchor;->mTarget:Landroid/support/constraint/solver/widgets/ConstraintAnchor;

    iget-object v6, v4, Landroid/support/constraint/solver/widgets/ConstraintAnchor;->mSolverVariable:Landroid/support/constraint/solver/SolverVariable;

    .line 453
    .local v6, "topTarget":Landroid/support/constraint/solver/SolverVariable;
    :goto_4
    move-object/from16 v0, v36

    iget-object v4, v0, Landroid/support/constraint/solver/widgets/ConstraintWidget;->mBottom:Landroid/support/constraint/solver/widgets/ConstraintAnchor;

    iget-object v10, v4, Landroid/support/constraint/solver/widgets/ConstraintAnchor;->mSolverVariable:Landroid/support/constraint/solver/SolverVariable;

    .line 454
    .local v10, "bottom":Landroid/support/constraint/solver/SolverVariable;
    move-object/from16 v0, v36

    iget-object v4, v0, Landroid/support/constraint/solver/widgets/ConstraintWidget;->mBottom:Landroid/support/constraint/solver/widgets/ConstraintAnchor;

    iget-object v4, v4, Landroid/support/constraint/solver/widgets/ConstraintAnchor;->mTarget:Landroid/support/constraint/solver/widgets/ConstraintAnchor;

    if-eqz v4, :cond_9

    move-object/from16 v0, v36

    iget-object v4, v0, Landroid/support/constraint/solver/widgets/ConstraintWidget;->mBottom:Landroid/support/constraint/solver/widgets/ConstraintAnchor;

    iget-object v4, v4, Landroid/support/constraint/solver/widgets/ConstraintAnchor;->mTarget:Landroid/support/constraint/solver/widgets/ConstraintAnchor;

    iget-object v9, v4, Landroid/support/constraint/solver/widgets/ConstraintAnchor;->mSolverVariable:Landroid/support/constraint/solver/SolverVariable;

    .line 455
    .local v9, "bottomTarget":Landroid/support/constraint/solver/SolverVariable;
    :goto_5
    move/from16 v29, v7

    .line 456
    .local v29, "margin":I
    if-eqz v34, :cond_4

    .line 457
    move-object/from16 v0, v34

    iget-object v4, v0, Landroid/support/constraint/solver/widgets/ConstraintWidget;->mBottom:Landroid/support/constraint/solver/widgets/ConstraintAnchor;

    invoke-virtual {v4}, Landroid/support/constraint/solver/widgets/ConstraintAnchor;->getMargin()I

    move-result v4

    add-int v29, v29, v4

    .line 459
    :cond_4
    if-eqz v6, :cond_5

    .line 460
    if-eqz v24, :cond_a

    move-object/from16 v0, v36

    move-object/from16 v1, v26

    if-eq v0, v1, :cond_a

    .line 461
    move-object/from16 v0, p1

    move/from16 v1, v29

    invoke-virtual {v0, v5, v6, v1}, Landroid/support/constraint/solver/LinearSystem;->addEquality(Landroid/support/constraint/solver/SolverVariable;Landroid/support/constraint/solver/SolverVariable;I)V

    .line 466
    :cond_5
    :goto_6
    if-eqz v9, :cond_7

    .line 467
    move/from16 v29, v11

    .line 468
    move-object/from16 v0, v36

    iget-object v4, v0, Landroid/support/constraint/solver/widgets/ConstraintWidget;->mBottom:Landroid/support/constraint/solver/widgets/ConstraintAnchor;

    iget-object v4, v4, Landroid/support/constraint/solver/widgets/ConstraintAnchor;->mTarget:Landroid/support/constraint/solver/widgets/ConstraintAnchor;

    iget-object v4, v4, Landroid/support/constraint/solver/widgets/ConstraintAnchor;->mOwner:Landroid/support/constraint/solver/widgets/ConstraintWidget;

    iget-object v0, v4, Landroid/support/constraint/solver/widgets/ConstraintWidget;->mTop:Landroid/support/constraint/solver/widgets/ConstraintAnchor;

    move-object/from16 v30, v0

    .line 469
    .local v30, "nextTop":Landroid/support/constraint/solver/widgets/ConstraintAnchor;
    move-object/from16 v0, v30

    iget-object v4, v0, Landroid/support/constraint/solver/widgets/ConstraintAnchor;->mTarget:Landroid/support/constraint/solver/widgets/ConstraintAnchor;

    if-eqz v4, :cond_b

    move-object/from16 v0, v30

    iget-object v4, v0, Landroid/support/constraint/solver/widgets/ConstraintAnchor;->mTarget:Landroid/support/constraint/solver/widgets/ConstraintAnchor;

    iget-object v0, v4, Landroid/support/constraint/solver/widgets/ConstraintAnchor;->mOwner:Landroid/support/constraint/solver/widgets/ConstraintWidget;

    move-object/from16 v31, v0

    .line 470
    .local v31, "nextTopTarget":Landroid/support/constraint/solver/widgets/ConstraintWidget;
    :goto_7
    move-object/from16 v0, v31

    move-object/from16 v1, v36

    if-ne v0, v1, :cond_6

    .line 471
    invoke-virtual/range {v30 .. v30}, Landroid/support/constraint/solver/widgets/ConstraintAnchor;->getMargin()I

    move-result v4

    add-int v29, v29, v4

    .line 473
    :cond_6
    if-eqz v24, :cond_c

    move-object/from16 v0, v31

    move-object/from16 v1, v36

    if-ne v0, v1, :cond_c

    .line 474
    move/from16 v0, v29

    neg-int v4, v0

    move-object/from16 v0, p1

    invoke-virtual {v0, v10, v9, v4}, Landroid/support/constraint/solver/LinearSystem;->addEquality(Landroid/support/constraint/solver/SolverVariable;Landroid/support/constraint/solver/SolverVariable;I)V

    .line 478
    :goto_8
    if-nez v24, :cond_7

    if-eqz v6, :cond_7

    .line 479
    const/high16 v8, 0x3f000000    # 0.5f

    move-object/from16 v4, p1

    invoke-virtual/range {v4 .. v11}, Landroid/support/constraint/solver/LinearSystem;->addCentering(Landroid/support/constraint/solver/SolverVariable;Landroid/support/constraint/solver/SolverVariable;IFLandroid/support/constraint/solver/SolverVariable;Landroid/support/constraint/solver/SolverVariable;I)V

    .line 483
    .end local v30    # "nextTop":Landroid/support/constraint/solver/widgets/ConstraintAnchor;
    .end local v31    # "nextTopTarget":Landroid/support/constraint/solver/widgets/ConstraintWidget;
    :cond_7
    move-object/from16 v34, v36

    .line 484
    if-eqz v9, :cond_d

    .line 485
    move-object/from16 v0, v36

    iget-object v4, v0, Landroid/support/constraint/solver/widgets/ConstraintWidget;->mBottom:Landroid/support/constraint/solver/widgets/ConstraintAnchor;

    iget-object v4, v4, Landroid/support/constraint/solver/widgets/ConstraintAnchor;->mTarget:Landroid/support/constraint/solver/widgets/ConstraintAnchor;

    iget-object v0, v4, Landroid/support/constraint/solver/widgets/ConstraintAnchor;->mOwner:Landroid/support/constraint/solver/widgets/ConstraintWidget;

    move-object/from16 v36, v0

    .line 489
    goto/16 :goto_3

    .line 452
    .end local v6    # "topTarget":Landroid/support/constraint/solver/SolverVariable;
    .end local v9    # "bottomTarget":Landroid/support/constraint/solver/SolverVariable;
    .end local v10    # "bottom":Landroid/support/constraint/solver/SolverVariable;
    .end local v29    # "margin":I
    :cond_8
    const/4 v6, 0x0

    goto/16 :goto_4

    .line 454
    .restart local v6    # "topTarget":Landroid/support/constraint/solver/SolverVariable;
    .restart local v10    # "bottom":Landroid/support/constraint/solver/SolverVariable;
    :cond_9
    const/4 v9, 0x0

    goto :goto_5

    .line 463
    .restart local v9    # "bottomTarget":Landroid/support/constraint/solver/SolverVariable;
    .restart local v29    # "margin":I
    :cond_a
    move-object/from16 v0, p1

    move/from16 v1, v29

    invoke-virtual {v0, v5, v6, v1}, Landroid/support/constraint/solver/LinearSystem;->addGreaterThan(Landroid/support/constraint/solver/SolverVariable;Landroid/support/constraint/solver/SolverVariable;I)V

    goto :goto_6

    .line 469
    .restart local v30    # "nextTop":Landroid/support/constraint/solver/widgets/ConstraintAnchor;
    :cond_b
    const/16 v31, 0x0

    goto :goto_7

    .line 476
    .restart local v31    # "nextTopTarget":Landroid/support/constraint/solver/widgets/ConstraintWidget;
    :cond_c
    move/from16 v0, v29

    neg-int v4, v0

    move-object/from16 v0, p1

    invoke-virtual {v0, v10, v9, v4}, Landroid/support/constraint/solver/LinearSystem;->addLowerThan(Landroid/support/constraint/solver/SolverVariable;Landroid/support/constraint/solver/SolverVariable;I)V

    goto :goto_8

    .line 490
    .end local v5    # "top":Landroid/support/constraint/solver/SolverVariable;
    .end local v6    # "topTarget":Landroid/support/constraint/solver/SolverVariable;
    .end local v7    # "topMargin":I
    .end local v9    # "bottomTarget":Landroid/support/constraint/solver/SolverVariable;
    .end local v10    # "bottom":Landroid/support/constraint/solver/SolverVariable;
    .end local v11    # "bottomMargin":I
    .end local v29    # "margin":I
    .end local v30    # "nextTop":Landroid/support/constraint/solver/widgets/ConstraintAnchor;
    .end local v31    # "nextTopTarget":Landroid/support/constraint/solver/widgets/ConstraintWidget;
    :cond_d
    if-eqz v24, :cond_0

    .line 491
    move-object/from16 v0, v26

    iget-object v4, v0, Landroid/support/constraint/solver/widgets/ConstraintWidget;->mTop:Landroid/support/constraint/solver/widgets/ConstraintAnchor;

    invoke-virtual {v4}, Landroid/support/constraint/solver/widgets/ConstraintAnchor;->getMargin()I

    move-result v7

    .line 492
    .restart local v7    # "topMargin":I
    move-object/from16 v0, v34

    iget-object v4, v0, Landroid/support/constraint/solver/widgets/ConstraintWidget;->mBottom:Landroid/support/constraint/solver/widgets/ConstraintAnchor;

    invoke-virtual {v4}, Landroid/support/constraint/solver/widgets/ConstraintAnchor;->getMargin()I

    move-result v11

    .line 493
    .restart local v11    # "bottomMargin":I
    move-object/from16 v0, v26

    iget-object v4, v0, Landroid/support/constraint/solver/widgets/ConstraintWidget;->mTop:Landroid/support/constraint/solver/widgets/ConstraintAnchor;

    iget-object v5, v4, Landroid/support/constraint/solver/widgets/ConstraintAnchor;->mSolverVariable:Landroid/support/constraint/solver/SolverVariable;

    .line 494
    .restart local v5    # "top":Landroid/support/constraint/solver/SolverVariable;
    move-object/from16 v0, v26

    iget-object v4, v0, Landroid/support/constraint/solver/widgets/ConstraintWidget;->mTop:Landroid/support/constraint/solver/widgets/ConstraintAnchor;

    iget-object v4, v4, Landroid/support/constraint/solver/widgets/ConstraintAnchor;->mTarget:Landroid/support/constraint/solver/widgets/ConstraintAnchor;

    if-eqz v4, :cond_e

    move-object/from16 v0, v26

    iget-object v4, v0, Landroid/support/constraint/solver/widgets/ConstraintWidget;->mTop:Landroid/support/constraint/solver/widgets/ConstraintAnchor;

    iget-object v4, v4, Landroid/support/constraint/solver/widgets/ConstraintAnchor;->mTarget:Landroid/support/constraint/solver/widgets/ConstraintAnchor;

    iget-object v6, v4, Landroid/support/constraint/solver/widgets/ConstraintAnchor;->mSolverVariable:Landroid/support/constraint/solver/SolverVariable;

    .line 495
    .restart local v6    # "topTarget":Landroid/support/constraint/solver/SolverVariable;
    :goto_9
    move-object/from16 v0, v34

    iget-object v4, v0, Landroid/support/constraint/solver/widgets/ConstraintWidget;->mBottom:Landroid/support/constraint/solver/widgets/ConstraintAnchor;

    iget-object v10, v4, Landroid/support/constraint/solver/widgets/ConstraintAnchor;->mSolverVariable:Landroid/support/constraint/solver/SolverVariable;

    .line 496
    .restart local v10    # "bottom":Landroid/support/constraint/solver/SolverVariable;
    move-object/from16 v0, v34

    iget-object v4, v0, Landroid/support/constraint/solver/widgets/ConstraintWidget;->mBottom:Landroid/support/constraint/solver/widgets/ConstraintAnchor;

    iget-object v4, v4, Landroid/support/constraint/solver/widgets/ConstraintAnchor;->mTarget:Landroid/support/constraint/solver/widgets/ConstraintAnchor;

    if-eqz v4, :cond_f

    move-object/from16 v0, v34

    iget-object v4, v0, Landroid/support/constraint/solver/widgets/ConstraintWidget;->mBottom:Landroid/support/constraint/solver/widgets/ConstraintAnchor;

    iget-object v4, v4, Landroid/support/constraint/solver/widgets/ConstraintAnchor;->mTarget:Landroid/support/constraint/solver/widgets/ConstraintAnchor;

    iget-object v9, v4, Landroid/support/constraint/solver/widgets/ConstraintAnchor;->mSolverVariable:Landroid/support/constraint/solver/SolverVariable;

    .line 497
    .restart local v9    # "bottomTarget":Landroid/support/constraint/solver/SolverVariable;
    :goto_a
    move-object/from16 v0, v26

    iget v8, v0, Landroid/support/constraint/solver/widgets/ConstraintWidget;->mVerticalBiasPercent:F

    move-object/from16 v4, p1

    invoke-virtual/range {v4 .. v11}, Landroid/support/constraint/solver/LinearSystem;->addCentering(Landroid/support/constraint/solver/SolverVariable;Landroid/support/constraint/solver/SolverVariable;IFLandroid/support/constraint/solver/SolverVariable;Landroid/support/constraint/solver/SolverVariable;I)V

    goto/16 :goto_2

    .line 494
    .end local v6    # "topTarget":Landroid/support/constraint/solver/SolverVariable;
    .end local v9    # "bottomTarget":Landroid/support/constraint/solver/SolverVariable;
    .end local v10    # "bottom":Landroid/support/constraint/solver/SolverVariable;
    :cond_e
    const/4 v6, 0x0

    goto :goto_9

    .line 496
    .restart local v6    # "topTarget":Landroid/support/constraint/solver/SolverVariable;
    .restart local v10    # "bottom":Landroid/support/constraint/solver/SolverVariable;
    :cond_f
    const/4 v9, 0x0

    goto :goto_a

    .line 501
    .end local v5    # "top":Landroid/support/constraint/solver/SolverVariable;
    .end local v6    # "topTarget":Landroid/support/constraint/solver/SolverVariable;
    .end local v7    # "topMargin":I
    .end local v10    # "bottom":Landroid/support/constraint/solver/SolverVariable;
    .end local v11    # "bottomMargin":I
    .end local v34    # "previous":Landroid/support/constraint/solver/widgets/ConstraintWidget;
    :cond_10
    const/16 v34, 0x0

    .line 502
    .restart local v34    # "previous":Landroid/support/constraint/solver/widgets/ConstraintWidget;
    const/4 v14, 0x0

    .line 503
    .local v14, "totalWeights":F
    :goto_b
    if-eqz v34, :cond_11

    move-object/from16 v0, v36

    iget-object v4, v0, Landroid/support/constraint/solver/widgets/ConstraintWidget;->mTop:Landroid/support/constraint/solver/widgets/ConstraintAnchor;

    iget-object v4, v4, Landroid/support/constraint/solver/widgets/ConstraintAnchor;->mTarget:Landroid/support/constraint/solver/widgets/ConstraintAnchor;

    if-eqz v4, :cond_15

    move-object/from16 v0, v36

    iget-object v4, v0, Landroid/support/constraint/solver/widgets/ConstraintWidget;->mTop:Landroid/support/constraint/solver/widgets/ConstraintAnchor;

    iget-object v4, v4, Landroid/support/constraint/solver/widgets/ConstraintAnchor;->mTarget:Landroid/support/constraint/solver/widgets/ConstraintAnchor;

    iget-object v4, v4, Landroid/support/constraint/solver/widgets/ConstraintAnchor;->mOwner:Landroid/support/constraint/solver/widgets/ConstraintWidget;

    move-object/from16 v0, v34

    if-ne v4, v0, :cond_15

    .line 504
    :cond_11
    move-object/from16 v0, v36

    iget-object v4, v0, Landroid/support/constraint/solver/widgets/ConstraintWidget;->mVerticalDimensionBehaviour:Landroid/support/constraint/solver/widgets/ConstraintWidget$DimensionBehaviour;

    sget-object v8, Landroid/support/constraint/solver/widgets/ConstraintWidget$DimensionBehaviour;->MATCH_CONSTRAINT:Landroid/support/constraint/solver/widgets/ConstraintWidget$DimensionBehaviour;

    if-eq v4, v8, :cond_14

    .line 505
    move-object/from16 v0, v36

    iget-object v4, v0, Landroid/support/constraint/solver/widgets/ConstraintWidget;->mTop:Landroid/support/constraint/solver/widgets/ConstraintAnchor;

    invoke-virtual {v4}, Landroid/support/constraint/solver/widgets/ConstraintAnchor;->getMargin()I

    move-result v29

    .line 506
    .restart local v29    # "margin":I
    if-eqz v34, :cond_12

    .line 507
    move-object/from16 v0, v34

    iget-object v4, v0, Landroid/support/constraint/solver/widgets/ConstraintWidget;->mBottom:Landroid/support/constraint/solver/widgets/ConstraintAnchor;

    invoke-virtual {v4}, Landroid/support/constraint/solver/widgets/ConstraintAnchor;->getMargin()I

    move-result v4

    add-int v29, v29, v4

    .line 509
    :cond_12
    move-object/from16 v0, v36

    iget-object v4, v0, Landroid/support/constraint/solver/widgets/ConstraintWidget;->mTop:Landroid/support/constraint/solver/widgets/ConstraintAnchor;

    iget-object v4, v4, Landroid/support/constraint/solver/widgets/ConstraintAnchor;->mSolverVariable:Landroid/support/constraint/solver/SolverVariable;

    move-object/from16 v0, v36

    iget-object v8, v0, Landroid/support/constraint/solver/widgets/ConstraintWidget;->mTop:Landroid/support/constraint/solver/widgets/ConstraintAnchor;

    iget-object v8, v8, Landroid/support/constraint/solver/widgets/ConstraintAnchor;->mTarget:Landroid/support/constraint/solver/widgets/ConstraintAnchor;

    iget-object v8, v8, Landroid/support/constraint/solver/widgets/ConstraintAnchor;->mSolverVariable:Landroid/support/constraint/solver/SolverVariable;

    move-object/from16 v0, p1

    move/from16 v1, v29

    invoke-virtual {v0, v4, v8, v1}, Landroid/support/constraint/solver/LinearSystem;->addGreaterThan(Landroid/support/constraint/solver/SolverVariable;Landroid/support/constraint/solver/SolverVariable;I)V

    .line 510
    move-object/from16 v0, v36

    iget-object v4, v0, Landroid/support/constraint/solver/widgets/ConstraintWidget;->mBottom:Landroid/support/constraint/solver/widgets/ConstraintAnchor;

    invoke-virtual {v4}, Landroid/support/constraint/solver/widgets/ConstraintAnchor;->getMargin()I

    move-result v29

    .line 511
    move-object/from16 v0, v36

    iget-object v4, v0, Landroid/support/constraint/solver/widgets/ConstraintWidget;->mBottom:Landroid/support/constraint/solver/widgets/ConstraintAnchor;

    iget-object v4, v4, Landroid/support/constraint/solver/widgets/ConstraintAnchor;->mTarget:Landroid/support/constraint/solver/widgets/ConstraintAnchor;

    iget-object v4, v4, Landroid/support/constraint/solver/widgets/ConstraintAnchor;->mOwner:Landroid/support/constraint/solver/widgets/ConstraintWidget;

    iget-object v4, v4, Landroid/support/constraint/solver/widgets/ConstraintWidget;->mTop:Landroid/support/constraint/solver/widgets/ConstraintAnchor;

    iget-object v4, v4, Landroid/support/constraint/solver/widgets/ConstraintAnchor;->mTarget:Landroid/support/constraint/solver/widgets/ConstraintAnchor;

    if-eqz v4, :cond_13

    move-object/from16 v0, v36

    iget-object v4, v0, Landroid/support/constraint/solver/widgets/ConstraintWidget;->mBottom:Landroid/support/constraint/solver/widgets/ConstraintAnchor;

    iget-object v4, v4, Landroid/support/constraint/solver/widgets/ConstraintAnchor;->mTarget:Landroid/support/constraint/solver/widgets/ConstraintAnchor;

    iget-object v4, v4, Landroid/support/constraint/solver/widgets/ConstraintAnchor;->mOwner:Landroid/support/constraint/solver/widgets/ConstraintWidget;

    iget-object v4, v4, Landroid/support/constraint/solver/widgets/ConstraintWidget;->mTop:Landroid/support/constraint/solver/widgets/ConstraintAnchor;

    iget-object v4, v4, Landroid/support/constraint/solver/widgets/ConstraintAnchor;->mTarget:Landroid/support/constraint/solver/widgets/ConstraintAnchor;

    iget-object v4, v4, Landroid/support/constraint/solver/widgets/ConstraintAnchor;->mOwner:Landroid/support/constraint/solver/widgets/ConstraintWidget;

    move-object/from16 v0, v36

    if-ne v4, v0, :cond_13

    .line 512
    move-object/from16 v0, v36

    iget-object v4, v0, Landroid/support/constraint/solver/widgets/ConstraintWidget;->mBottom:Landroid/support/constraint/solver/widgets/ConstraintAnchor;

    iget-object v4, v4, Landroid/support/constraint/solver/widgets/ConstraintAnchor;->mTarget:Landroid/support/constraint/solver/widgets/ConstraintAnchor;

    iget-object v4, v4, Landroid/support/constraint/solver/widgets/ConstraintAnchor;->mOwner:Landroid/support/constraint/solver/widgets/ConstraintWidget;

    iget-object v4, v4, Landroid/support/constraint/solver/widgets/ConstraintWidget;->mTop:Landroid/support/constraint/solver/widgets/ConstraintAnchor;

    invoke-virtual {v4}, Landroid/support/constraint/solver/widgets/ConstraintAnchor;->getMargin()I

    move-result v4

    add-int v29, v29, v4

    .line 514
    :cond_13
    move-object/from16 v0, v36

    iget-object v4, v0, Landroid/support/constraint/solver/widgets/ConstraintWidget;->mBottom:Landroid/support/constraint/solver/widgets/ConstraintAnchor;

    iget-object v4, v4, Landroid/support/constraint/solver/widgets/ConstraintAnchor;->mSolverVariable:Landroid/support/constraint/solver/SolverVariable;

    move-object/from16 v0, v36

    iget-object v8, v0, Landroid/support/constraint/solver/widgets/ConstraintWidget;->mBottom:Landroid/support/constraint/solver/widgets/ConstraintAnchor;

    iget-object v8, v8, Landroid/support/constraint/solver/widgets/ConstraintAnchor;->mTarget:Landroid/support/constraint/solver/widgets/ConstraintAnchor;

    iget-object v8, v8, Landroid/support/constraint/solver/widgets/ConstraintAnchor;->mSolverVariable:Landroid/support/constraint/solver/SolverVariable;

    move/from16 v0, v29

    neg-int v13, v0

    move-object/from16 v0, p1

    invoke-virtual {v0, v4, v8, v13}, Landroid/support/constraint/solver/LinearSystem;->addLowerThan(Landroid/support/constraint/solver/SolverVariable;Landroid/support/constraint/solver/SolverVariable;I)V

    .line 518
    .end local v29    # "margin":I
    :goto_c
    move-object/from16 v34, v36

    .line 519
    move-object/from16 v0, v36

    iget-object v4, v0, Landroid/support/constraint/solver/widgets/ConstraintWidget;->mBottom:Landroid/support/constraint/solver/widgets/ConstraintAnchor;

    iget-object v4, v4, Landroid/support/constraint/solver/widgets/ConstraintAnchor;->mTarget:Landroid/support/constraint/solver/widgets/ConstraintAnchor;

    iget-object v0, v4, Landroid/support/constraint/solver/widgets/ConstraintAnchor;->mOwner:Landroid/support/constraint/solver/widgets/ConstraintWidget;

    move-object/from16 v36, v0

    goto/16 :goto_b

    .line 516
    :cond_14
    move-object/from16 v0, v36

    iget v4, v0, Landroid/support/constraint/solver/widgets/ConstraintWidget;->mVerticalWeight:F

    add-float/2addr v14, v4

    goto :goto_c

    .line 521
    :cond_15
    const/4 v4, 0x1

    move/from16 v0, v33

    if-ne v0, v4, :cond_16

    .line 522
    move-object/from16 v0, p0

    iget-object v4, v0, Landroid/support/constraint/solver/widgets/ConstraintWidgetContainer;->mMatchConstraintsChainedWidgets:[Landroid/support/constraint/solver/widgets/ConstraintWidget;

    const/4 v8, 0x0

    aget-object v35, v4, v8

    .line 523
    .local v35, "w":Landroid/support/constraint/solver/widgets/ConstraintWidget;
    move-object/from16 v0, v35

    iget-object v4, v0, Landroid/support/constraint/solver/widgets/ConstraintWidget;->mTop:Landroid/support/constraint/solver/widgets/ConstraintAnchor;

    iget-object v4, v4, Landroid/support/constraint/solver/widgets/ConstraintAnchor;->mSolverVariable:Landroid/support/constraint/solver/SolverVariable;

    move-object/from16 v0, v35

    iget-object v8, v0, Landroid/support/constraint/solver/widgets/ConstraintWidget;->mTop:Landroid/support/constraint/solver/widgets/ConstraintAnchor;

    iget-object v8, v8, Landroid/support/constraint/solver/widgets/ConstraintAnchor;->mTarget:Landroid/support/constraint/solver/widgets/ConstraintAnchor;

    iget-object v8, v8, Landroid/support/constraint/solver/widgets/ConstraintAnchor;->mSolverVariable:Landroid/support/constraint/solver/SolverVariable;

    move-object/from16 v0, v35

    iget-object v13, v0, Landroid/support/constraint/solver/widgets/ConstraintWidget;->mTop:Landroid/support/constraint/solver/widgets/ConstraintAnchor;

    invoke-virtual {v13}, Landroid/support/constraint/solver/widgets/ConstraintAnchor;->getMargin()I

    move-result v13

    move-object/from16 v0, p1

    invoke-virtual {v0, v4, v8, v13}, Landroid/support/constraint/solver/LinearSystem;->addEquality(Landroid/support/constraint/solver/SolverVariable;Landroid/support/constraint/solver/SolverVariable;I)V

    .line 524
    move-object/from16 v0, v35

    iget-object v4, v0, Landroid/support/constraint/solver/widgets/ConstraintWidget;->mBottom:Landroid/support/constraint/solver/widgets/ConstraintAnchor;

    iget-object v4, v4, Landroid/support/constraint/solver/widgets/ConstraintAnchor;->mSolverVariable:Landroid/support/constraint/solver/SolverVariable;

    move-object/from16 v0, v35

    iget-object v8, v0, Landroid/support/constraint/solver/widgets/ConstraintWidget;->mBottom:Landroid/support/constraint/solver/widgets/ConstraintAnchor;

    iget-object v8, v8, Landroid/support/constraint/solver/widgets/ConstraintAnchor;->mTarget:Landroid/support/constraint/solver/widgets/ConstraintAnchor;

    iget-object v8, v8, Landroid/support/constraint/solver/widgets/ConstraintAnchor;->mSolverVariable:Landroid/support/constraint/solver/SolverVariable;

    move-object/from16 v0, v35

    iget-object v13, v0, Landroid/support/constraint/solver/widgets/ConstraintWidget;->mBottom:Landroid/support/constraint/solver/widgets/ConstraintAnchor;

    invoke-virtual {v13}, Landroid/support/constraint/solver/widgets/ConstraintAnchor;->getMargin()I

    move-result v13

    mul-int/lit8 v13, v13, -0x1

    move-object/from16 v0, p1

    invoke-virtual {v0, v4, v8, v13}, Landroid/support/constraint/solver/LinearSystem;->addEquality(Landroid/support/constraint/solver/SolverVariable;Landroid/support/constraint/solver/SolverVariable;I)V

    .line 525
    move-object/from16 v0, v35

    iget-object v4, v0, Landroid/support/constraint/solver/widgets/ConstraintWidget;->mBottom:Landroid/support/constraint/solver/widgets/ConstraintAnchor;

    iget-object v4, v4, Landroid/support/constraint/solver/widgets/ConstraintAnchor;->mSolverVariable:Landroid/support/constraint/solver/SolverVariable;

    move-object/from16 v0, v35

    iget-object v8, v0, Landroid/support/constraint/solver/widgets/ConstraintWidget;->mBottom:Landroid/support/constraint/solver/widgets/ConstraintAnchor;

    iget-object v8, v8, Landroid/support/constraint/solver/widgets/ConstraintAnchor;->mTarget:Landroid/support/constraint/solver/widgets/ConstraintAnchor;

    iget-object v8, v8, Landroid/support/constraint/solver/widgets/ConstraintAnchor;->mSolverVariable:Landroid/support/constraint/solver/SolverVariable;

    const/4 v13, 0x0

    move-object/from16 v0, p1

    invoke-virtual {v0, v4, v8, v13}, Landroid/support/constraint/solver/LinearSystem;->addLowerThan(Landroid/support/constraint/solver/SolverVariable;Landroid/support/constraint/solver/SolverVariable;I)V

    goto/16 :goto_2

    .line 527
    .end local v35    # "w":Landroid/support/constraint/solver/widgets/ConstraintWidget;
    :cond_16
    const/16 v28, 0x0

    .local v28, "j":I
    :goto_d
    add-int/lit8 v4, v33, -0x1

    move/from16 v0, v28

    if-ge v0, v4, :cond_0

    .line 528
    move-object/from16 v0, p0

    iget-object v4, v0, Landroid/support/constraint/solver/widgets/ConstraintWidgetContainer;->mMatchConstraintsChainedWidgets:[Landroid/support/constraint/solver/widgets/ConstraintWidget;

    aget-object v25, v4, v28

    .line 529
    .local v25, "current":Landroid/support/constraint/solver/widgets/ConstraintWidget;
    move-object/from16 v0, p0

    iget-object v4, v0, Landroid/support/constraint/solver/widgets/ConstraintWidgetContainer;->mMatchConstraintsChainedWidgets:[Landroid/support/constraint/solver/widgets/ConstraintWidget;

    add-int/lit8 v8, v28, 0x1

    aget-object v32, v4, v8

    .line 530
    .local v32, "nextWidget":Landroid/support/constraint/solver/widgets/ConstraintWidget;
    move-object/from16 v0, v25

    iget-object v4, v0, Landroid/support/constraint/solver/widgets/ConstraintWidget;->mTop:Landroid/support/constraint/solver/widgets/ConstraintAnchor;

    iget-object v5, v4, Landroid/support/constraint/solver/widgets/ConstraintAnchor;->mSolverVariable:Landroid/support/constraint/solver/SolverVariable;

    .line 531
    .restart local v5    # "top":Landroid/support/constraint/solver/SolverVariable;
    move-object/from16 v0, v25

    iget-object v4, v0, Landroid/support/constraint/solver/widgets/ConstraintWidget;->mBottom:Landroid/support/constraint/solver/widgets/ConstraintAnchor;

    iget-object v10, v4, Landroid/support/constraint/solver/widgets/ConstraintAnchor;->mSolverVariable:Landroid/support/constraint/solver/SolverVariable;

    .line 532
    .restart local v10    # "bottom":Landroid/support/constraint/solver/SolverVariable;
    move-object/from16 v0, v32

    iget-object v4, v0, Landroid/support/constraint/solver/widgets/ConstraintWidget;->mTop:Landroid/support/constraint/solver/widgets/ConstraintAnchor;

    iget-object v0, v4, Landroid/support/constraint/solver/widgets/ConstraintAnchor;->mSolverVariable:Landroid/support/constraint/solver/SolverVariable;

    move-object/from16 v20, v0

    .line 533
    .local v20, "nextLeft":Landroid/support/constraint/solver/SolverVariable;
    move-object/from16 v0, v32

    iget-object v4, v0, Landroid/support/constraint/solver/widgets/ConstraintWidget;->mBottom:Landroid/support/constraint/solver/widgets/ConstraintAnchor;

    iget-object v0, v4, Landroid/support/constraint/solver/widgets/ConstraintAnchor;->mSolverVariable:Landroid/support/constraint/solver/SolverVariable;

    move-object/from16 v22, v0

    .line 534
    .local v22, "nextRight":Landroid/support/constraint/solver/SolverVariable;
    move-object/from16 v0, v25

    iget-object v4, v0, Landroid/support/constraint/solver/widgets/ConstraintWidget;->mTop:Landroid/support/constraint/solver/widgets/ConstraintAnchor;

    invoke-virtual {v4}, Landroid/support/constraint/solver/widgets/ConstraintAnchor;->getMargin()I

    move-result v29

    .line 535
    .restart local v29    # "margin":I
    move-object/from16 v0, v25

    iget-object v4, v0, Landroid/support/constraint/solver/widgets/ConstraintWidget;->mTop:Landroid/support/constraint/solver/widgets/ConstraintAnchor;

    iget-object v4, v4, Landroid/support/constraint/solver/widgets/ConstraintAnchor;->mTarget:Landroid/support/constraint/solver/widgets/ConstraintAnchor;

    if-eqz v4, :cond_17

    move-object/from16 v0, v25

    iget-object v4, v0, Landroid/support/constraint/solver/widgets/ConstraintWidget;->mTop:Landroid/support/constraint/solver/widgets/ConstraintAnchor;

    iget-object v4, v4, Landroid/support/constraint/solver/widgets/ConstraintAnchor;->mTarget:Landroid/support/constraint/solver/widgets/ConstraintAnchor;

    iget-object v4, v4, Landroid/support/constraint/solver/widgets/ConstraintAnchor;->mOwner:Landroid/support/constraint/solver/widgets/ConstraintWidget;

    iget-object v4, v4, Landroid/support/constraint/solver/widgets/ConstraintWidget;->mBottom:Landroid/support/constraint/solver/widgets/ConstraintAnchor;

    iget-object v4, v4, Landroid/support/constraint/solver/widgets/ConstraintAnchor;->mTarget:Landroid/support/constraint/solver/widgets/ConstraintAnchor;

    if-eqz v4, :cond_17

    move-object/from16 v0, v25

    iget-object v4, v0, Landroid/support/constraint/solver/widgets/ConstraintWidget;->mTop:Landroid/support/constraint/solver/widgets/ConstraintAnchor;

    iget-object v4, v4, Landroid/support/constraint/solver/widgets/ConstraintAnchor;->mTarget:Landroid/support/constraint/solver/widgets/ConstraintAnchor;

    iget-object v4, v4, Landroid/support/constraint/solver/widgets/ConstraintAnchor;->mOwner:Landroid/support/constraint/solver/widgets/ConstraintWidget;

    iget-object v4, v4, Landroid/support/constraint/solver/widgets/ConstraintWidget;->mBottom:Landroid/support/constraint/solver/widgets/ConstraintAnchor;

    iget-object v4, v4, Landroid/support/constraint/solver/widgets/ConstraintAnchor;->mTarget:Landroid/support/constraint/solver/widgets/ConstraintAnchor;

    iget-object v4, v4, Landroid/support/constraint/solver/widgets/ConstraintAnchor;->mOwner:Landroid/support/constraint/solver/widgets/ConstraintWidget;

    move-object/from16 v0, v25

    if-ne v4, v0, :cond_17

    .line 537
    move-object/from16 v0, v25

    iget-object v4, v0, Landroid/support/constraint/solver/widgets/ConstraintWidget;->mTop:Landroid/support/constraint/solver/widgets/ConstraintAnchor;

    iget-object v4, v4, Landroid/support/constraint/solver/widgets/ConstraintAnchor;->mTarget:Landroid/support/constraint/solver/widgets/ConstraintAnchor;

    iget-object v4, v4, Landroid/support/constraint/solver/widgets/ConstraintAnchor;->mOwner:Landroid/support/constraint/solver/widgets/ConstraintWidget;

    iget-object v4, v4, Landroid/support/constraint/solver/widgets/ConstraintWidget;->mBottom:Landroid/support/constraint/solver/widgets/ConstraintAnchor;

    invoke-virtual {v4}, Landroid/support/constraint/solver/widgets/ConstraintAnchor;->getMargin()I

    move-result v4

    add-int v29, v29, v4

    .line 539
    :cond_17
    move-object/from16 v0, v25

    iget-object v4, v0, Landroid/support/constraint/solver/widgets/ConstraintWidget;->mTop:Landroid/support/constraint/solver/widgets/ConstraintAnchor;

    iget-object v4, v4, Landroid/support/constraint/solver/widgets/ConstraintAnchor;->mTarget:Landroid/support/constraint/solver/widgets/ConstraintAnchor;

    iget-object v4, v4, Landroid/support/constraint/solver/widgets/ConstraintAnchor;->mSolverVariable:Landroid/support/constraint/solver/SolverVariable;

    move-object/from16 v0, p1

    move/from16 v1, v29

    invoke-virtual {v0, v5, v4, v1}, Landroid/support/constraint/solver/LinearSystem;->addGreaterThan(Landroid/support/constraint/solver/SolverVariable;Landroid/support/constraint/solver/SolverVariable;I)V

    .line 540
    move-object/from16 v0, v25

    iget-object v4, v0, Landroid/support/constraint/solver/widgets/ConstraintWidget;->mBottom:Landroid/support/constraint/solver/widgets/ConstraintAnchor;

    invoke-virtual {v4}, Landroid/support/constraint/solver/widgets/ConstraintAnchor;->getMargin()I

    move-result v29

    .line 541
    move-object/from16 v0, v25

    iget-object v4, v0, Landroid/support/constraint/solver/widgets/ConstraintWidget;->mBottom:Landroid/support/constraint/solver/widgets/ConstraintAnchor;

    iget-object v4, v4, Landroid/support/constraint/solver/widgets/ConstraintAnchor;->mTarget:Landroid/support/constraint/solver/widgets/ConstraintAnchor;

    if-eqz v4, :cond_18

    move-object/from16 v0, v25

    iget-object v4, v0, Landroid/support/constraint/solver/widgets/ConstraintWidget;->mBottom:Landroid/support/constraint/solver/widgets/ConstraintAnchor;

    iget-object v4, v4, Landroid/support/constraint/solver/widgets/ConstraintAnchor;->mTarget:Landroid/support/constraint/solver/widgets/ConstraintAnchor;

    iget-object v4, v4, Landroid/support/constraint/solver/widgets/ConstraintAnchor;->mOwner:Landroid/support/constraint/solver/widgets/ConstraintWidget;

    iget-object v4, v4, Landroid/support/constraint/solver/widgets/ConstraintWidget;->mTop:Landroid/support/constraint/solver/widgets/ConstraintAnchor;

    iget-object v4, v4, Landroid/support/constraint/solver/widgets/ConstraintAnchor;->mTarget:Landroid/support/constraint/solver/widgets/ConstraintAnchor;

    if-eqz v4, :cond_18

    move-object/from16 v0, v25

    iget-object v4, v0, Landroid/support/constraint/solver/widgets/ConstraintWidget;->mBottom:Landroid/support/constraint/solver/widgets/ConstraintAnchor;

    iget-object v4, v4, Landroid/support/constraint/solver/widgets/ConstraintAnchor;->mTarget:Landroid/support/constraint/solver/widgets/ConstraintAnchor;

    iget-object v4, v4, Landroid/support/constraint/solver/widgets/ConstraintAnchor;->mOwner:Landroid/support/constraint/solver/widgets/ConstraintWidget;

    iget-object v4, v4, Landroid/support/constraint/solver/widgets/ConstraintWidget;->mTop:Landroid/support/constraint/solver/widgets/ConstraintAnchor;

    iget-object v4, v4, Landroid/support/constraint/solver/widgets/ConstraintAnchor;->mTarget:Landroid/support/constraint/solver/widgets/ConstraintAnchor;

    iget-object v4, v4, Landroid/support/constraint/solver/widgets/ConstraintAnchor;->mOwner:Landroid/support/constraint/solver/widgets/ConstraintWidget;

    move-object/from16 v0, v25

    if-ne v4, v0, :cond_18

    .line 543
    move-object/from16 v0, v25

    iget-object v4, v0, Landroid/support/constraint/solver/widgets/ConstraintWidget;->mBottom:Landroid/support/constraint/solver/widgets/ConstraintAnchor;

    iget-object v4, v4, Landroid/support/constraint/solver/widgets/ConstraintAnchor;->mTarget:Landroid/support/constraint/solver/widgets/ConstraintAnchor;

    iget-object v4, v4, Landroid/support/constraint/solver/widgets/ConstraintAnchor;->mOwner:Landroid/support/constraint/solver/widgets/ConstraintWidget;

    iget-object v4, v4, Landroid/support/constraint/solver/widgets/ConstraintWidget;->mTop:Landroid/support/constraint/solver/widgets/ConstraintAnchor;

    invoke-virtual {v4}, Landroid/support/constraint/solver/widgets/ConstraintAnchor;->getMargin()I

    move-result v4

    add-int v29, v29, v4

    .line 545
    :cond_18
    move-object/from16 v0, v25

    iget-object v4, v0, Landroid/support/constraint/solver/widgets/ConstraintWidget;->mBottom:Landroid/support/constraint/solver/widgets/ConstraintAnchor;

    iget-object v4, v4, Landroid/support/constraint/solver/widgets/ConstraintAnchor;->mTarget:Landroid/support/constraint/solver/widgets/ConstraintAnchor;

    iget-object v4, v4, Landroid/support/constraint/solver/widgets/ConstraintAnchor;->mSolverVariable:Landroid/support/constraint/solver/SolverVariable;

    move/from16 v0, v29

    neg-int v8, v0

    move-object/from16 v0, p1

    invoke-virtual {v0, v10, v4, v8}, Landroid/support/constraint/solver/LinearSystem;->addLowerThan(Landroid/support/constraint/solver/SolverVariable;Landroid/support/constraint/solver/SolverVariable;I)V

    .line 546
    add-int/lit8 v4, v28, 0x1

    add-int/lit8 v8, v33, -0x1

    if-ne v4, v8, :cond_1b

    .line 548
    move-object/from16 v0, v32

    iget-object v4, v0, Landroid/support/constraint/solver/widgets/ConstraintWidget;->mTop:Landroid/support/constraint/solver/widgets/ConstraintAnchor;

    invoke-virtual {v4}, Landroid/support/constraint/solver/widgets/ConstraintAnchor;->getMargin()I

    move-result v29

    .line 549
    move-object/from16 v0, v32

    iget-object v4, v0, Landroid/support/constraint/solver/widgets/ConstraintWidget;->mTop:Landroid/support/constraint/solver/widgets/ConstraintAnchor;

    iget-object v4, v4, Landroid/support/constraint/solver/widgets/ConstraintAnchor;->mTarget:Landroid/support/constraint/solver/widgets/ConstraintAnchor;

    if-eqz v4, :cond_19

    move-object/from16 v0, v32

    iget-object v4, v0, Landroid/support/constraint/solver/widgets/ConstraintWidget;->mTop:Landroid/support/constraint/solver/widgets/ConstraintAnchor;

    iget-object v4, v4, Landroid/support/constraint/solver/widgets/ConstraintAnchor;->mTarget:Landroid/support/constraint/solver/widgets/ConstraintAnchor;

    iget-object v4, v4, Landroid/support/constraint/solver/widgets/ConstraintAnchor;->mOwner:Landroid/support/constraint/solver/widgets/ConstraintWidget;

    iget-object v4, v4, Landroid/support/constraint/solver/widgets/ConstraintWidget;->mBottom:Landroid/support/constraint/solver/widgets/ConstraintAnchor;

    iget-object v4, v4, Landroid/support/constraint/solver/widgets/ConstraintAnchor;->mTarget:Landroid/support/constraint/solver/widgets/ConstraintAnchor;

    if-eqz v4, :cond_19

    move-object/from16 v0, v32

    iget-object v4, v0, Landroid/support/constraint/solver/widgets/ConstraintWidget;->mTop:Landroid/support/constraint/solver/widgets/ConstraintAnchor;

    iget-object v4, v4, Landroid/support/constraint/solver/widgets/ConstraintAnchor;->mTarget:Landroid/support/constraint/solver/widgets/ConstraintAnchor;

    iget-object v4, v4, Landroid/support/constraint/solver/widgets/ConstraintAnchor;->mOwner:Landroid/support/constraint/solver/widgets/ConstraintWidget;

    iget-object v4, v4, Landroid/support/constraint/solver/widgets/ConstraintWidget;->mBottom:Landroid/support/constraint/solver/widgets/ConstraintAnchor;

    iget-object v4, v4, Landroid/support/constraint/solver/widgets/ConstraintAnchor;->mTarget:Landroid/support/constraint/solver/widgets/ConstraintAnchor;

    iget-object v4, v4, Landroid/support/constraint/solver/widgets/ConstraintAnchor;->mOwner:Landroid/support/constraint/solver/widgets/ConstraintWidget;

    move-object/from16 v0, v32

    if-ne v4, v0, :cond_19

    .line 551
    move-object/from16 v0, v32

    iget-object v4, v0, Landroid/support/constraint/solver/widgets/ConstraintWidget;->mTop:Landroid/support/constraint/solver/widgets/ConstraintAnchor;

    iget-object v4, v4, Landroid/support/constraint/solver/widgets/ConstraintAnchor;->mTarget:Landroid/support/constraint/solver/widgets/ConstraintAnchor;

    iget-object v4, v4, Landroid/support/constraint/solver/widgets/ConstraintAnchor;->mOwner:Landroid/support/constraint/solver/widgets/ConstraintWidget;

    iget-object v4, v4, Landroid/support/constraint/solver/widgets/ConstraintWidget;->mBottom:Landroid/support/constraint/solver/widgets/ConstraintAnchor;

    invoke-virtual {v4}, Landroid/support/constraint/solver/widgets/ConstraintAnchor;->getMargin()I

    move-result v4

    add-int v29, v29, v4

    .line 553
    :cond_19
    move-object/from16 v0, v32

    iget-object v4, v0, Landroid/support/constraint/solver/widgets/ConstraintWidget;->mTop:Landroid/support/constraint/solver/widgets/ConstraintAnchor;

    iget-object v4, v4, Landroid/support/constraint/solver/widgets/ConstraintAnchor;->mTarget:Landroid/support/constraint/solver/widgets/ConstraintAnchor;

    iget-object v4, v4, Landroid/support/constraint/solver/widgets/ConstraintAnchor;->mSolverVariable:Landroid/support/constraint/solver/SolverVariable;

    move-object/from16 v0, p1

    move-object/from16 v1, v20

    move/from16 v2, v29

    invoke-virtual {v0, v1, v4, v2}, Landroid/support/constraint/solver/LinearSystem;->addGreaterThan(Landroid/support/constraint/solver/SolverVariable;Landroid/support/constraint/solver/SolverVariable;I)V

    .line 554
    move-object/from16 v0, v32

    iget-object v4, v0, Landroid/support/constraint/solver/widgets/ConstraintWidget;->mBottom:Landroid/support/constraint/solver/widgets/ConstraintAnchor;

    invoke-virtual {v4}, Landroid/support/constraint/solver/widgets/ConstraintAnchor;->getMargin()I

    move-result v29

    .line 555
    move-object/from16 v0, v32

    iget-object v4, v0, Landroid/support/constraint/solver/widgets/ConstraintWidget;->mBottom:Landroid/support/constraint/solver/widgets/ConstraintAnchor;

    iget-object v4, v4, Landroid/support/constraint/solver/widgets/ConstraintAnchor;->mTarget:Landroid/support/constraint/solver/widgets/ConstraintAnchor;

    if-eqz v4, :cond_1a

    move-object/from16 v0, v32

    iget-object v4, v0, Landroid/support/constraint/solver/widgets/ConstraintWidget;->mBottom:Landroid/support/constraint/solver/widgets/ConstraintAnchor;

    iget-object v4, v4, Landroid/support/constraint/solver/widgets/ConstraintAnchor;->mTarget:Landroid/support/constraint/solver/widgets/ConstraintAnchor;

    iget-object v4, v4, Landroid/support/constraint/solver/widgets/ConstraintAnchor;->mOwner:Landroid/support/constraint/solver/widgets/ConstraintWidget;

    iget-object v4, v4, Landroid/support/constraint/solver/widgets/ConstraintWidget;->mTop:Landroid/support/constraint/solver/widgets/ConstraintAnchor;

    iget-object v4, v4, Landroid/support/constraint/solver/widgets/ConstraintAnchor;->mTarget:Landroid/support/constraint/solver/widgets/ConstraintAnchor;

    if-eqz v4, :cond_1a

    move-object/from16 v0, v32

    iget-object v4, v0, Landroid/support/constraint/solver/widgets/ConstraintWidget;->mBottom:Landroid/support/constraint/solver/widgets/ConstraintAnchor;

    iget-object v4, v4, Landroid/support/constraint/solver/widgets/ConstraintAnchor;->mTarget:Landroid/support/constraint/solver/widgets/ConstraintAnchor;

    iget-object v4, v4, Landroid/support/constraint/solver/widgets/ConstraintAnchor;->mOwner:Landroid/support/constraint/solver/widgets/ConstraintWidget;

    iget-object v4, v4, Landroid/support/constraint/solver/widgets/ConstraintWidget;->mTop:Landroid/support/constraint/solver/widgets/ConstraintAnchor;

    iget-object v4, v4, Landroid/support/constraint/solver/widgets/ConstraintAnchor;->mTarget:Landroid/support/constraint/solver/widgets/ConstraintAnchor;

    iget-object v4, v4, Landroid/support/constraint/solver/widgets/ConstraintAnchor;->mOwner:Landroid/support/constraint/solver/widgets/ConstraintWidget;

    move-object/from16 v0, v32

    if-ne v4, v0, :cond_1a

    .line 557
    move-object/from16 v0, v32

    iget-object v4, v0, Landroid/support/constraint/solver/widgets/ConstraintWidget;->mBottom:Landroid/support/constraint/solver/widgets/ConstraintAnchor;

    iget-object v4, v4, Landroid/support/constraint/solver/widgets/ConstraintAnchor;->mTarget:Landroid/support/constraint/solver/widgets/ConstraintAnchor;

    iget-object v4, v4, Landroid/support/constraint/solver/widgets/ConstraintAnchor;->mOwner:Landroid/support/constraint/solver/widgets/ConstraintWidget;

    iget-object v4, v4, Landroid/support/constraint/solver/widgets/ConstraintWidget;->mTop:Landroid/support/constraint/solver/widgets/ConstraintAnchor;

    invoke-virtual {v4}, Landroid/support/constraint/solver/widgets/ConstraintAnchor;->getMargin()I

    move-result v4

    add-int v29, v29, v4

    .line 559
    :cond_1a
    move-object/from16 v0, v32

    iget-object v4, v0, Landroid/support/constraint/solver/widgets/ConstraintWidget;->mBottom:Landroid/support/constraint/solver/widgets/ConstraintAnchor;

    iget-object v4, v4, Landroid/support/constraint/solver/widgets/ConstraintAnchor;->mTarget:Landroid/support/constraint/solver/widgets/ConstraintAnchor;

    iget-object v4, v4, Landroid/support/constraint/solver/widgets/ConstraintAnchor;->mSolverVariable:Landroid/support/constraint/solver/SolverVariable;

    move/from16 v0, v29

    neg-int v8, v0

    move-object/from16 v0, p1

    move-object/from16 v1, v22

    invoke-virtual {v0, v1, v4, v8}, Landroid/support/constraint/solver/LinearSystem;->addLowerThan(Landroid/support/constraint/solver/SolverVariable;Landroid/support/constraint/solver/SolverVariable;I)V

    .line 562
    :cond_1b
    invoke-virtual/range {p1 .. p1}, Landroid/support/constraint/solver/LinearSystem;->createRow()Landroid/support/constraint/solver/ArrayRow;

    move-result-object v12

    .line 563
    .local v12, "row":Landroid/support/constraint/solver/ArrayRow;
    move-object/from16 v0, v25

    iget v13, v0, Landroid/support/constraint/solver/widgets/ConstraintWidget;->mVerticalWeight:F

    move-object/from16 v0, v32

    iget v15, v0, Landroid/support/constraint/solver/widgets/ConstraintWidget;->mVerticalWeight:F

    move-object/from16 v0, v25

    iget-object v4, v0, Landroid/support/constraint/solver/widgets/ConstraintWidget;->mTop:Landroid/support/constraint/solver/widgets/ConstraintAnchor;

    .line 565
    invoke-virtual {v4}, Landroid/support/constraint/solver/widgets/ConstraintAnchor;->getMargin()I

    move-result v17

    move-object/from16 v0, v25

    iget-object v4, v0, Landroid/support/constraint/solver/widgets/ConstraintWidget;->mBottom:Landroid/support/constraint/solver/widgets/ConstraintAnchor;

    .line 566
    invoke-virtual {v4}, Landroid/support/constraint/solver/widgets/ConstraintAnchor;->getMargin()I

    move-result v19

    move-object/from16 v0, v32

    iget-object v4, v0, Landroid/support/constraint/solver/widgets/ConstraintWidget;->mTop:Landroid/support/constraint/solver/widgets/ConstraintAnchor;

    .line 567
    invoke-virtual {v4}, Landroid/support/constraint/solver/widgets/ConstraintAnchor;->getMargin()I

    move-result v21

    move-object/from16 v0, v32

    iget-object v4, v0, Landroid/support/constraint/solver/widgets/ConstraintWidget;->mBottom:Landroid/support/constraint/solver/widgets/ConstraintAnchor;

    .line 568
    invoke-virtual {v4}, Landroid/support/constraint/solver/widgets/ConstraintAnchor;->getMargin()I

    move-result v23

    move-object/from16 v16, v5

    move-object/from16 v18, v10

    .line 563
    invoke-virtual/range {v12 .. v23}, Landroid/support/constraint/solver/ArrayRow;->createRowEqualDimension(FFFLandroid/support/constraint/solver/SolverVariable;ILandroid/support/constraint/solver/SolverVariable;ILandroid/support/constraint/solver/SolverVariable;ILandroid/support/constraint/solver/SolverVariable;I)Landroid/support/constraint/solver/ArrayRow;

    .line 569
    move-object/from16 v0, p1

    invoke-virtual {v0, v12}, Landroid/support/constraint/solver/LinearSystem;->addConstraint(Landroid/support/constraint/solver/ArrayRow;)V

    .line 527
    add-int/lit8 v28, v28, 0x1

    goto/16 :goto_d

    .line 575
    .end local v5    # "top":Landroid/support/constraint/solver/SolverVariable;
    .end local v10    # "bottom":Landroid/support/constraint/solver/SolverVariable;
    .end local v12    # "row":Landroid/support/constraint/solver/ArrayRow;
    .end local v14    # "totalWeights":F
    .end local v20    # "nextLeft":Landroid/support/constraint/solver/SolverVariable;
    .end local v22    # "nextRight":Landroid/support/constraint/solver/SolverVariable;
    .end local v24    # "chainPacked":Z
    .end local v25    # "current":Landroid/support/constraint/solver/widgets/ConstraintWidget;
    .end local v26    # "first":Landroid/support/constraint/solver/widgets/ConstraintWidget;
    .end local v28    # "j":I
    .end local v29    # "margin":I
    .end local v32    # "nextWidget":Landroid/support/constraint/solver/widgets/ConstraintWidget;
    .end local v33    # "numMatchConstraints":I
    .end local v34    # "previous":Landroid/support/constraint/solver/widgets/ConstraintWidget;
    .end local v36    # "widget":Landroid/support/constraint/solver/widgets/ConstraintWidget;
    :cond_1c
    return-void
.end method

.method private countMatchConstraintsChainedWidgets(Landroid/support/constraint/solver/widgets/ConstraintWidget;I)I
    .locals 6
    .param p1, "widget"    # Landroid/support/constraint/solver/widgets/ConstraintWidget;
    .param p2, "direction"    # I

    .prologue
    .line 1395
    const/4 v0, 0x0

    .line 1396
    .local v0, "count":I
    if-nez p2, :cond_6

    .line 1397
    const/4 v3, 0x1

    .line 1398
    .local v3, "fixedPosition":Z
    move-object v2, p1

    .line 1399
    .local v2, "first":Landroid/support/constraint/solver/widgets/ConstraintWidget;
    iget-object v4, p1, Landroid/support/constraint/solver/widgets/ConstraintWidget;->mLeft:Landroid/support/constraint/solver/widgets/ConstraintAnchor;

    iget-object v4, v4, Landroid/support/constraint/solver/widgets/ConstraintAnchor;->mTarget:Landroid/support/constraint/solver/widgets/ConstraintAnchor;

    if-eqz v4, :cond_0

    iget-object v4, p1, Landroid/support/constraint/solver/widgets/ConstraintWidget;->mLeft:Landroid/support/constraint/solver/widgets/ConstraintAnchor;

    iget-object v4, v4, Landroid/support/constraint/solver/widgets/ConstraintAnchor;->mTarget:Landroid/support/constraint/solver/widgets/ConstraintAnchor;

    iget-object v4, v4, Landroid/support/constraint/solver/widgets/ConstraintAnchor;->mOwner:Landroid/support/constraint/solver/widgets/ConstraintWidget;

    if-eq v4, p0, :cond_0

    .line 1400
    const/4 v3, 0x0

    .line 1402
    :cond_0
    :goto_0
    iget-object v4, p1, Landroid/support/constraint/solver/widgets/ConstraintWidget;->mRight:Landroid/support/constraint/solver/widgets/ConstraintAnchor;

    iget-object v4, v4, Landroid/support/constraint/solver/widgets/ConstraintAnchor;->mTarget:Landroid/support/constraint/solver/widgets/ConstraintAnchor;

    if-eqz v4, :cond_3

    .line 1403
    iget-object v4, p1, Landroid/support/constraint/solver/widgets/ConstraintWidget;->mHorizontalDimensionBehaviour:Landroid/support/constraint/solver/widgets/ConstraintWidget$DimensionBehaviour;

    sget-object v5, Landroid/support/constraint/solver/widgets/ConstraintWidget$DimensionBehaviour;->MATCH_CONSTRAINT:Landroid/support/constraint/solver/widgets/ConstraintWidget$DimensionBehaviour;

    if-ne v4, v5, :cond_2

    .line 1404
    add-int/lit8 v4, v0, 0x1

    iget-object v5, p0, Landroid/support/constraint/solver/widgets/ConstraintWidgetContainer;->mMatchConstraintsChainedWidgets:[Landroid/support/constraint/solver/widgets/ConstraintWidget;

    array-length v5, v5

    if-lt v4, v5, :cond_1

    .line 1405
    iget-object v4, p0, Landroid/support/constraint/solver/widgets/ConstraintWidgetContainer;->mMatchConstraintsChainedWidgets:[Landroid/support/constraint/solver/widgets/ConstraintWidget;

    iget-object v5, p0, Landroid/support/constraint/solver/widgets/ConstraintWidgetContainer;->mMatchConstraintsChainedWidgets:[Landroid/support/constraint/solver/widgets/ConstraintWidget;

    array-length v5, v5

    mul-int/lit8 v5, v5, 0x2

    invoke-static {v4, v5}, Ljava/util/Arrays;->copyOf([Ljava/lang/Object;I)[Ljava/lang/Object;

    move-result-object v4

    check-cast v4, [Landroid/support/constraint/solver/widgets/ConstraintWidget;

    iput-object v4, p0, Landroid/support/constraint/solver/widgets/ConstraintWidgetContainer;->mMatchConstraintsChainedWidgets:[Landroid/support/constraint/solver/widgets/ConstraintWidget;

    .line 1407
    :cond_1
    iget-object v4, p0, Landroid/support/constraint/solver/widgets/ConstraintWidgetContainer;->mMatchConstraintsChainedWidgets:[Landroid/support/constraint/solver/widgets/ConstraintWidget;

    add-int/lit8 v1, v0, 0x1

    .end local v0    # "count":I
    .local v1, "count":I
    aput-object p1, v4, v0

    move v0, v1

    .line 1409
    .end local v1    # "count":I
    .restart local v0    # "count":I
    :cond_2
    iget-object v4, p1, Landroid/support/constraint/solver/widgets/ConstraintWidget;->mRight:Landroid/support/constraint/solver/widgets/ConstraintAnchor;

    iget-object v4, v4, Landroid/support/constraint/solver/widgets/ConstraintAnchor;->mTarget:Landroid/support/constraint/solver/widgets/ConstraintAnchor;

    iget-object v4, v4, Landroid/support/constraint/solver/widgets/ConstraintAnchor;->mOwner:Landroid/support/constraint/solver/widgets/ConstraintWidget;

    iget-object v4, v4, Landroid/support/constraint/solver/widgets/ConstraintWidget;->mLeft:Landroid/support/constraint/solver/widgets/ConstraintAnchor;

    iget-object v4, v4, Landroid/support/constraint/solver/widgets/ConstraintAnchor;->mTarget:Landroid/support/constraint/solver/widgets/ConstraintAnchor;

    if-nez v4, :cond_5

    .line 1417
    :cond_3
    iget-object v4, p1, Landroid/support/constraint/solver/widgets/ConstraintWidget;->mRight:Landroid/support/constraint/solver/widgets/ConstraintAnchor;

    iget-object v4, v4, Landroid/support/constraint/solver/widgets/ConstraintAnchor;->mTarget:Landroid/support/constraint/solver/widgets/ConstraintAnchor;

    if-eqz v4, :cond_4

    iget-object v4, p1, Landroid/support/constraint/solver/widgets/ConstraintWidget;->mRight:Landroid/support/constraint/solver/widgets/ConstraintAnchor;

    iget-object v4, v4, Landroid/support/constraint/solver/widgets/ConstraintAnchor;->mTarget:Landroid/support/constraint/solver/widgets/ConstraintAnchor;

    iget-object v4, v4, Landroid/support/constraint/solver/widgets/ConstraintAnchor;->mOwner:Landroid/support/constraint/solver/widgets/ConstraintWidget;

    if-eq v4, p0, :cond_4

    .line 1418
    const/4 v3, 0x0

    .line 1422
    :cond_4
    iput-boolean v3, v2, Landroid/support/constraint/solver/widgets/ConstraintWidget;->mHorizontalChainFixedPosition:Z

    .line 1451
    :goto_1
    return v0

    .line 1412
    :cond_5
    iget-object v4, p1, Landroid/support/constraint/solver/widgets/ConstraintWidget;->mRight:Landroid/support/constraint/solver/widgets/ConstraintAnchor;

    iget-object v4, v4, Landroid/support/constraint/solver/widgets/ConstraintAnchor;->mTarget:Landroid/support/constraint/solver/widgets/ConstraintAnchor;

    iget-object v4, v4, Landroid/support/constraint/solver/widgets/ConstraintAnchor;->mOwner:Landroid/support/constraint/solver/widgets/ConstraintWidget;

    iget-object v4, v4, Landroid/support/constraint/solver/widgets/ConstraintWidget;->mLeft:Landroid/support/constraint/solver/widgets/ConstraintAnchor;

    iget-object v4, v4, Landroid/support/constraint/solver/widgets/ConstraintAnchor;->mTarget:Landroid/support/constraint/solver/widgets/ConstraintAnchor;

    iget-object v4, v4, Landroid/support/constraint/solver/widgets/ConstraintAnchor;->mOwner:Landroid/support/constraint/solver/widgets/ConstraintWidget;

    if-ne v4, p1, :cond_3

    .line 1415
    iget-object v4, p1, Landroid/support/constraint/solver/widgets/ConstraintWidget;->mRight:Landroid/support/constraint/solver/widgets/ConstraintAnchor;

    iget-object v4, v4, Landroid/support/constraint/solver/widgets/ConstraintAnchor;->mTarget:Landroid/support/constraint/solver/widgets/ConstraintAnchor;

    iget-object p1, v4, Landroid/support/constraint/solver/widgets/ConstraintAnchor;->mOwner:Landroid/support/constraint/solver/widgets/ConstraintWidget;

    goto :goto_0

    .line 1424
    .end local v2    # "first":Landroid/support/constraint/solver/widgets/ConstraintWidget;
    .end local v3    # "fixedPosition":Z
    :cond_6
    const/4 v3, 0x1

    .line 1425
    .restart local v3    # "fixedPosition":Z
    move-object v2, p1

    .line 1426
    .restart local v2    # "first":Landroid/support/constraint/solver/widgets/ConstraintWidget;
    iget-object v4, p1, Landroid/support/constraint/solver/widgets/ConstraintWidget;->mTop:Landroid/support/constraint/solver/widgets/ConstraintAnchor;

    iget-object v4, v4, Landroid/support/constraint/solver/widgets/ConstraintAnchor;->mTarget:Landroid/support/constraint/solver/widgets/ConstraintAnchor;

    if-eqz v4, :cond_7

    iget-object v4, p1, Landroid/support/constraint/solver/widgets/ConstraintWidget;->mTop:Landroid/support/constraint/solver/widgets/ConstraintAnchor;

    iget-object v4, v4, Landroid/support/constraint/solver/widgets/ConstraintAnchor;->mTarget:Landroid/support/constraint/solver/widgets/ConstraintAnchor;

    iget-object v4, v4, Landroid/support/constraint/solver/widgets/ConstraintAnchor;->mOwner:Landroid/support/constraint/solver/widgets/ConstraintWidget;

    if-eq v4, p0, :cond_7

    .line 1427
    const/4 v3, 0x0

    .line 1429
    :cond_7
    :goto_2
    iget-object v4, p1, Landroid/support/constraint/solver/widgets/ConstraintWidget;->mBottom:Landroid/support/constraint/solver/widgets/ConstraintAnchor;

    iget-object v4, v4, Landroid/support/constraint/solver/widgets/ConstraintAnchor;->mTarget:Landroid/support/constraint/solver/widgets/ConstraintAnchor;

    if-eqz v4, :cond_a

    .line 1430
    iget-object v4, p1, Landroid/support/constraint/solver/widgets/ConstraintWidget;->mVerticalDimensionBehaviour:Landroid/support/constraint/solver/widgets/ConstraintWidget$DimensionBehaviour;

    sget-object v5, Landroid/support/constraint/solver/widgets/ConstraintWidget$DimensionBehaviour;->MATCH_CONSTRAINT:Landroid/support/constraint/solver/widgets/ConstraintWidget$DimensionBehaviour;

    if-ne v4, v5, :cond_9

    .line 1431
    add-int/lit8 v4, v0, 0x1

    iget-object v5, p0, Landroid/support/constraint/solver/widgets/ConstraintWidgetContainer;->mMatchConstraintsChainedWidgets:[Landroid/support/constraint/solver/widgets/ConstraintWidget;

    array-length v5, v5

    if-lt v4, v5, :cond_8

    .line 1432
    iget-object v4, p0, Landroid/support/constraint/solver/widgets/ConstraintWidgetContainer;->mMatchConstraintsChainedWidgets:[Landroid/support/constraint/solver/widgets/ConstraintWidget;

    iget-object v5, p0, Landroid/support/constraint/solver/widgets/ConstraintWidgetContainer;->mMatchConstraintsChainedWidgets:[Landroid/support/constraint/solver/widgets/ConstraintWidget;

    array-length v5, v5

    mul-int/lit8 v5, v5, 0x2

    invoke-static {v4, v5}, Ljava/util/Arrays;->copyOf([Ljava/lang/Object;I)[Ljava/lang/Object;

    move-result-object v4

    check-cast v4, [Landroid/support/constraint/solver/widgets/ConstraintWidget;

    iput-object v4, p0, Landroid/support/constraint/solver/widgets/ConstraintWidgetContainer;->mMatchConstraintsChainedWidgets:[Landroid/support/constraint/solver/widgets/ConstraintWidget;

    .line 1434
    :cond_8
    iget-object v4, p0, Landroid/support/constraint/solver/widgets/ConstraintWidgetContainer;->mMatchConstraintsChainedWidgets:[Landroid/support/constraint/solver/widgets/ConstraintWidget;

    add-int/lit8 v1, v0, 0x1

    .end local v0    # "count":I
    .restart local v1    # "count":I
    aput-object p1, v4, v0

    move v0, v1

    .line 1436
    .end local v1    # "count":I
    .restart local v0    # "count":I
    :cond_9
    iget-object v4, p1, Landroid/support/constraint/solver/widgets/ConstraintWidget;->mBottom:Landroid/support/constraint/solver/widgets/ConstraintAnchor;

    iget-object v4, v4, Landroid/support/constraint/solver/widgets/ConstraintAnchor;->mTarget:Landroid/support/constraint/solver/widgets/ConstraintAnchor;

    iget-object v4, v4, Landroid/support/constraint/solver/widgets/ConstraintAnchor;->mOwner:Landroid/support/constraint/solver/widgets/ConstraintWidget;

    iget-object v4, v4, Landroid/support/constraint/solver/widgets/ConstraintWidget;->mTop:Landroid/support/constraint/solver/widgets/ConstraintAnchor;

    iget-object v4, v4, Landroid/support/constraint/solver/widgets/ConstraintAnchor;->mTarget:Landroid/support/constraint/solver/widgets/ConstraintAnchor;

    if-nez v4, :cond_c

    .line 1444
    :cond_a
    iget-object v4, p1, Landroid/support/constraint/solver/widgets/ConstraintWidget;->mBottom:Landroid/support/constraint/solver/widgets/ConstraintAnchor;

    iget-object v4, v4, Landroid/support/constraint/solver/widgets/ConstraintAnchor;->mTarget:Landroid/support/constraint/solver/widgets/ConstraintAnchor;

    if-eqz v4, :cond_b

    iget-object v4, p1, Landroid/support/constraint/solver/widgets/ConstraintWidget;->mBottom:Landroid/support/constraint/solver/widgets/ConstraintAnchor;

    iget-object v4, v4, Landroid/support/constraint/solver/widgets/ConstraintAnchor;->mTarget:Landroid/support/constraint/solver/widgets/ConstraintAnchor;

    iget-object v4, v4, Landroid/support/constraint/solver/widgets/ConstraintAnchor;->mOwner:Landroid/support/constraint/solver/widgets/ConstraintWidget;

    if-eq v4, p0, :cond_b

    .line 1445
    const/4 v3, 0x0

    .line 1449
    :cond_b
    iput-boolean v3, v2, Landroid/support/constraint/solver/widgets/ConstraintWidget;->mVerticalChainFixedPosition:Z

    goto :goto_1

    .line 1439
    :cond_c
    iget-object v4, p1, Landroid/support/constraint/solver/widgets/ConstraintWidget;->mBottom:Landroid/support/constraint/solver/widgets/ConstraintAnchor;

    iget-object v4, v4, Landroid/support/constraint/solver/widgets/ConstraintAnchor;->mTarget:Landroid/support/constraint/solver/widgets/ConstraintAnchor;

    iget-object v4, v4, Landroid/support/constraint/solver/widgets/ConstraintAnchor;->mOwner:Landroid/support/constraint/solver/widgets/ConstraintWidget;

    iget-object v4, v4, Landroid/support/constraint/solver/widgets/ConstraintWidget;->mTop:Landroid/support/constraint/solver/widgets/ConstraintAnchor;

    iget-object v4, v4, Landroid/support/constraint/solver/widgets/ConstraintAnchor;->mTarget:Landroid/support/constraint/solver/widgets/ConstraintAnchor;

    iget-object v4, v4, Landroid/support/constraint/solver/widgets/ConstraintAnchor;->mOwner:Landroid/support/constraint/solver/widgets/ConstraintWidget;

    if-ne v4, p1, :cond_a

    .line 1442
    iget-object v4, p1, Landroid/support/constraint/solver/widgets/ConstraintWidget;->mBottom:Landroid/support/constraint/solver/widgets/ConstraintAnchor;

    iget-object v4, v4, Landroid/support/constraint/solver/widgets/ConstraintAnchor;->mTarget:Landroid/support/constraint/solver/widgets/ConstraintAnchor;

    iget-object p1, v4, Landroid/support/constraint/solver/widgets/ConstraintAnchor;->mOwner:Landroid/support/constraint/solver/widgets/ConstraintWidget;

    goto :goto_2
.end method

.method public static createContainer(Landroid/support/constraint/solver/widgets/ConstraintWidgetContainer;Ljava/lang/String;Ljava/util/ArrayList;I)Landroid/support/constraint/solver/widgets/ConstraintWidgetContainer;
    .locals 8
    .param p0, "container"    # Landroid/support/constraint/solver/widgets/ConstraintWidgetContainer;
    .param p1, "name"    # Ljava/lang/String;
    .param p3, "padding"    # I
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "(",
            "Landroid/support/constraint/solver/widgets/ConstraintWidgetContainer;",
            "Ljava/lang/String;",
            "Ljava/util/ArrayList",
            "<",
            "Landroid/support/constraint/solver/widgets/ConstraintWidget;",
            ">;I)",
            "Landroid/support/constraint/solver/widgets/ConstraintWidgetContainer;"
        }
    .end annotation

    .prologue
    .line 125
    .local p2, "widgets":Ljava/util/ArrayList;, "Ljava/util/ArrayList<Landroid/support/constraint/solver/widgets/ConstraintWidget;>;"
    invoke-static {p2}, Landroid/support/constraint/solver/widgets/ConstraintWidgetContainer;->getBounds(Ljava/util/ArrayList;)Landroid/support/constraint/solver/widgets/Rectangle;

    move-result-object v0

    .line 126
    .local v0, "bounds":Landroid/support/constraint/solver/widgets/Rectangle;
    iget v6, v0, Landroid/support/constraint/solver/widgets/Rectangle;->width:I

    if-eqz v6, :cond_0

    iget v6, v0, Landroid/support/constraint/solver/widgets/Rectangle;->height:I

    if-nez v6, :cond_2

    .line 127
    :cond_0
    const/4 p0, 0x0

    .line 150
    .end local p0    # "container":Landroid/support/constraint/solver/widgets/ConstraintWidgetContainer;
    :cond_1
    return-object p0

    .line 129
    .restart local p0    # "container":Landroid/support/constraint/solver/widgets/ConstraintWidgetContainer;
    :cond_2
    if-lez p3, :cond_4

    .line 130
    iget v6, v0, Landroid/support/constraint/solver/widgets/Rectangle;->x:I

    iget v7, v0, Landroid/support/constraint/solver/widgets/Rectangle;->y:I

    invoke-static {v6, v7}, Ljava/lang/Math;->min(II)I

    move-result v2

    .line 131
    .local v2, "maxPadding":I
    if-le p3, v2, :cond_3

    .line 132
    move p3, v2

    .line 134
    :cond_3
    invoke-virtual {v0, p3, p3}, Landroid/support/constraint/solver/widgets/Rectangle;->grow(II)V

    .line 136
    .end local v2    # "maxPadding":I
    :cond_4
    iget v6, v0, Landroid/support/constraint/solver/widgets/Rectangle;->x:I

    iget v7, v0, Landroid/support/constraint/solver/widgets/Rectangle;->y:I

    invoke-virtual {p0, v6, v7}, Landroid/support/constraint/solver/widgets/ConstraintWidgetContainer;->setOrigin(II)V

    .line 137
    iget v6, v0, Landroid/support/constraint/solver/widgets/Rectangle;->width:I

    iget v7, v0, Landroid/support/constraint/solver/widgets/Rectangle;->height:I

    invoke-virtual {p0, v6, v7}, Landroid/support/constraint/solver/widgets/ConstraintWidgetContainer;->setDimension(II)V

    .line 138
    invoke-virtual {p0, p1}, Landroid/support/constraint/solver/widgets/ConstraintWidgetContainer;->setDebugName(Ljava/lang/String;)V

    .line 140
    const/4 v6, 0x0

    invoke-virtual {p2, v6}, Ljava/util/ArrayList;->get(I)Ljava/lang/Object;

    move-result-object v6

    check-cast v6, Landroid/support/constraint/solver/widgets/ConstraintWidget;

    invoke-virtual {v6}, Landroid/support/constraint/solver/widgets/ConstraintWidget;->getParent()Landroid/support/constraint/solver/widgets/ConstraintWidget;

    move-result-object v3

    .line 141
    .local v3, "parent":Landroid/support/constraint/solver/widgets/ConstraintWidget;
    const/4 v1, 0x0

    .local v1, "i":I
    invoke-virtual {p2}, Ljava/util/ArrayList;->size()I

    move-result v5

    .local v5, "widgetsSize":I
    :goto_0
    if-ge v1, v5, :cond_1

    .line 142
    invoke-virtual {p2, v1}, Ljava/util/ArrayList;->get(I)Ljava/lang/Object;

    move-result-object v4

    check-cast v4, Landroid/support/constraint/solver/widgets/ConstraintWidget;

    .line 143
    .local v4, "widget":Landroid/support/constraint/solver/widgets/ConstraintWidget;
    invoke-virtual {v4}, Landroid/support/constraint/solver/widgets/ConstraintWidget;->getParent()Landroid/support/constraint/solver/widgets/ConstraintWidget;

    move-result-object v6

    if-eq v6, v3, :cond_5

    .line 141
    :goto_1
    add-int/lit8 v1, v1, 0x1

    goto :goto_0

    .line 146
    :cond_5
    invoke-virtual {p0, v4}, Landroid/support/constraint/solver/widgets/ConstraintWidgetContainer;->add(Landroid/support/constraint/solver/widgets/ConstraintWidget;)V

    .line 147
    invoke-virtual {v4}, Landroid/support/constraint/solver/widgets/ConstraintWidget;->getX()I

    move-result v6

    iget v7, v0, Landroid/support/constraint/solver/widgets/Rectangle;->x:I

    sub-int/2addr v6, v7

    invoke-virtual {v4, v6}, Landroid/support/constraint/solver/widgets/ConstraintWidget;->setX(I)V

    .line 148
    invoke-virtual {v4}, Landroid/support/constraint/solver/widgets/ConstraintWidget;->getY()I

    move-result v6

    iget v7, v0, Landroid/support/constraint/solver/widgets/Rectangle;->y:I

    sub-int/2addr v6, v7

    invoke-virtual {v4, v6}, Landroid/support/constraint/solver/widgets/ConstraintWidget;->setY(I)V

    goto :goto_1
.end method

.method private resetChains()V
    .locals 1

    .prologue
    const/4 v0, 0x0

    .line 1320
    iput v0, p0, Landroid/support/constraint/solver/widgets/ConstraintWidgetContainer;->mHorizontalChainsSize:I

    .line 1321
    iput v0, p0, Landroid/support/constraint/solver/widgets/ConstraintWidgetContainer;->mVerticalChainsSize:I

    .line 1322
    return-void
.end method

.method static setGroup(Landroid/support/constraint/solver/widgets/ConstraintAnchor;I)I
    .locals 4
    .param p0, "anchor"    # Landroid/support/constraint/solver/widgets/ConstraintAnchor;
    .param p1, "group"    # I

    .prologue
    .line 779
    iget v0, p0, Landroid/support/constraint/solver/widgets/ConstraintAnchor;->mGroup:I

    .line 780
    .local v0, "oldGroup":I
    iget-object v3, p0, Landroid/support/constraint/solver/widgets/ConstraintAnchor;->mOwner:Landroid/support/constraint/solver/widgets/ConstraintWidget;

    invoke-virtual {v3}, Landroid/support/constraint/solver/widgets/ConstraintWidget;->getParent()Landroid/support/constraint/solver/widgets/ConstraintWidget;

    move-result-object v3

    if-nez v3, :cond_1

    move v0, p1

    .line 797
    .end local v0    # "oldGroup":I
    :cond_0
    :goto_0
    return v0

    .line 783
    .restart local v0    # "oldGroup":I
    :cond_1
    if-le v0, p1, :cond_0

    .line 787
    iput p1, p0, Landroid/support/constraint/solver/widgets/ConstraintAnchor;->mGroup:I

    .line 788
    invoke-virtual {p0}, Landroid/support/constraint/solver/widgets/ConstraintAnchor;->getOpposite()Landroid/support/constraint/solver/widgets/ConstraintAnchor;

    move-result-object v1

    .line 789
    .local v1, "opposite":Landroid/support/constraint/solver/widgets/ConstraintAnchor;
    iget-object v2, p0, Landroid/support/constraint/solver/widgets/ConstraintAnchor;->mTarget:Landroid/support/constraint/solver/widgets/ConstraintAnchor;

    .line 791
    .local v2, "target":Landroid/support/constraint/solver/widgets/ConstraintAnchor;
    if-eqz v1, :cond_2

    invoke-static {v1, p1}, Landroid/support/constraint/solver/widgets/ConstraintWidgetContainer;->setGroup(Landroid/support/constraint/solver/widgets/ConstraintAnchor;I)I

    move-result p1

    .line 792
    :cond_2
    if-eqz v2, :cond_3

    invoke-static {v2, p1}, Landroid/support/constraint/solver/widgets/ConstraintWidgetContainer;->setGroup(Landroid/support/constraint/solver/widgets/ConstraintAnchor;I)I

    move-result p1

    .line 793
    :cond_3
    if-eqz v1, :cond_4

    invoke-static {v1, p1}, Landroid/support/constraint/solver/widgets/ConstraintWidgetContainer;->setGroup(Landroid/support/constraint/solver/widgets/ConstraintAnchor;I)I

    move-result p1

    .line 795
    :cond_4
    iput p1, p0, Landroid/support/constraint/solver/widgets/ConstraintAnchor;->mGroup:I

    move v0, p1

    .line 797
    goto :goto_0
.end method


# virtual methods
.method addChain(Landroid/support/constraint/solver/widgets/ConstraintWidget;I)V
    .locals 2
    .param p1, "constraintWidget"    # Landroid/support/constraint/solver/widgets/ConstraintWidget;
    .param p2, "type"    # I

    .prologue
    .line 1331
    move-object v0, p1

    .line 1332
    .local v0, "widget":Landroid/support/constraint/solver/widgets/ConstraintWidget;
    if-nez p2, :cond_2

    .line 1334
    :goto_0
    iget-object v1, v0, Landroid/support/constraint/solver/widgets/ConstraintWidget;->mLeft:Landroid/support/constraint/solver/widgets/ConstraintAnchor;

    iget-object v1, v1, Landroid/support/constraint/solver/widgets/ConstraintAnchor;->mTarget:Landroid/support/constraint/solver/widgets/ConstraintAnchor;

    if-eqz v1, :cond_0

    iget-object v1, v0, Landroid/support/constraint/solver/widgets/ConstraintWidget;->mLeft:Landroid/support/constraint/solver/widgets/ConstraintAnchor;

    iget-object v1, v1, Landroid/support/constraint/solver/widgets/ConstraintAnchor;->mTarget:Landroid/support/constraint/solver/widgets/ConstraintAnchor;

    iget-object v1, v1, Landroid/support/constraint/solver/widgets/ConstraintAnchor;->mOwner:Landroid/support/constraint/solver/widgets/ConstraintWidget;

    iget-object v1, v1, Landroid/support/constraint/solver/widgets/ConstraintWidget;->mRight:Landroid/support/constraint/solver/widgets/ConstraintAnchor;

    iget-object v1, v1, Landroid/support/constraint/solver/widgets/ConstraintAnchor;->mTarget:Landroid/support/constraint/solver/widgets/ConstraintAnchor;

    if-eqz v1, :cond_0

    iget-object v1, v0, Landroid/support/constraint/solver/widgets/ConstraintWidget;->mLeft:Landroid/support/constraint/solver/widgets/ConstraintAnchor;

    iget-object v1, v1, Landroid/support/constraint/solver/widgets/ConstraintAnchor;->mTarget:Landroid/support/constraint/solver/widgets/ConstraintAnchor;

    iget-object v1, v1, Landroid/support/constraint/solver/widgets/ConstraintAnchor;->mOwner:Landroid/support/constraint/solver/widgets/ConstraintWidget;

    iget-object v1, v1, Landroid/support/constraint/solver/widgets/ConstraintWidget;->mRight:Landroid/support/constraint/solver/widgets/ConstraintAnchor;

    iget-object v1, v1, Landroid/support/constraint/solver/widgets/ConstraintAnchor;->mTarget:Landroid/support/constraint/solver/widgets/ConstraintAnchor;

    iget-object v1, v1, Landroid/support/constraint/solver/widgets/ConstraintAnchor;->mOwner:Landroid/support/constraint/solver/widgets/ConstraintWidget;

    if-ne v1, v0, :cond_0

    .line 1337
    iget-object v1, v0, Landroid/support/constraint/solver/widgets/ConstraintWidget;->mLeft:Landroid/support/constraint/solver/widgets/ConstraintAnchor;

    iget-object v1, v1, Landroid/support/constraint/solver/widgets/ConstraintAnchor;->mTarget:Landroid/support/constraint/solver/widgets/ConstraintAnchor;

    iget-object v0, v1, Landroid/support/constraint/solver/widgets/ConstraintAnchor;->mOwner:Landroid/support/constraint/solver/widgets/ConstraintWidget;

    goto :goto_0

    .line 1339
    :cond_0
    invoke-direct {p0, v0}, Landroid/support/constraint/solver/widgets/ConstraintWidgetContainer;->addHorizontalChain(Landroid/support/constraint/solver/widgets/ConstraintWidget;)V

    .line 1349
    :cond_1
    :goto_1
    return-void

    .line 1340
    :cond_2
    const/4 v1, 0x1

    if-ne p2, v1, :cond_1

    .line 1342
    :goto_2
    iget-object v1, v0, Landroid/support/constraint/solver/widgets/ConstraintWidget;->mTop:Landroid/support/constraint/solver/widgets/ConstraintAnchor;

    iget-object v1, v1, Landroid/support/constraint/solver/widgets/ConstraintAnchor;->mTarget:Landroid/support/constraint/solver/widgets/ConstraintAnchor;

    if-eqz v1, :cond_3

    iget-object v1, v0, Landroid/support/constraint/solver/widgets/ConstraintWidget;->mTop:Landroid/support/constraint/solver/widgets/ConstraintAnchor;

    iget-object v1, v1, Landroid/support/constraint/solver/widgets/ConstraintAnchor;->mTarget:Landroid/support/constraint/solver/widgets/ConstraintAnchor;

    iget-object v1, v1, Landroid/support/constraint/solver/widgets/ConstraintAnchor;->mOwner:Landroid/support/constraint/solver/widgets/ConstraintWidget;

    iget-object v1, v1, Landroid/support/constraint/solver/widgets/ConstraintWidget;->mBottom:Landroid/support/constraint/solver/widgets/ConstraintAnchor;

    iget-object v1, v1, Landroid/support/constraint/solver/widgets/ConstraintAnchor;->mTarget:Landroid/support/constraint/solver/widgets/ConstraintAnchor;

    if-eqz v1, :cond_3

    iget-object v1, v0, Landroid/support/constraint/solver/widgets/ConstraintWidget;->mTop:Landroid/support/constraint/solver/widgets/ConstraintAnchor;

    iget-object v1, v1, Landroid/support/constraint/solver/widgets/ConstraintAnchor;->mTarget:Landroid/support/constraint/solver/widgets/ConstraintAnchor;

    iget-object v1, v1, Landroid/support/constraint/solver/widgets/ConstraintAnchor;->mOwner:Landroid/support/constraint/solver/widgets/ConstraintWidget;

    iget-object v1, v1, Landroid/support/constraint/solver/widgets/ConstraintWidget;->mBottom:Landroid/support/constraint/solver/widgets/ConstraintAnchor;

    iget-object v1, v1, Landroid/support/constraint/solver/widgets/ConstraintAnchor;->mTarget:Landroid/support/constraint/solver/widgets/ConstraintAnchor;

    iget-object v1, v1, Landroid/support/constraint/solver/widgets/ConstraintAnchor;->mOwner:Landroid/support/constraint/solver/widgets/ConstraintWidget;

    if-ne v1, v0, :cond_3

    .line 1345
    iget-object v1, v0, Landroid/support/constraint/solver/widgets/ConstraintWidget;->mTop:Landroid/support/constraint/solver/widgets/ConstraintAnchor;

    iget-object v1, v1, Landroid/support/constraint/solver/widgets/ConstraintAnchor;->mTarget:Landroid/support/constraint/solver/widgets/ConstraintAnchor;

    iget-object v0, v1, Landroid/support/constraint/solver/widgets/ConstraintAnchor;->mOwner:Landroid/support/constraint/solver/widgets/ConstraintWidget;

    goto :goto_2

    .line 1347
    :cond_3
    invoke-direct {p0, v0}, Landroid/support/constraint/solver/widgets/ConstraintWidgetContainer;->addVerticalChain(Landroid/support/constraint/solver/widgets/ConstraintWidget;)V

    goto :goto_1
.end method

.method public addChildrenToSolver(Landroid/support/constraint/solver/LinearSystem;I)V
    .locals 6
    .param p1, "system"    # Landroid/support/constraint/solver/LinearSystem;
    .param p2, "group"    # I

    .prologue
    .line 163
    invoke-virtual {p0, p1, p2}, Landroid/support/constraint/solver/widgets/ConstraintWidgetContainer;->addToSolver(Landroid/support/constraint/solver/LinearSystem;I)V

    .line 164
    iget-object v5, p0, Landroid/support/constraint/solver/widgets/ConstraintWidgetContainer;->mChildren:Ljava/util/ArrayList;

    invoke-virtual {v5}, Ljava/util/ArrayList;->size()I

    move-result v0

    .line 165
    .local v0, "count":I
    const/4 v2, 0x0

    .local v2, "i":I
    :goto_0
    if-ge v2, v0, :cond_5

    .line 166
    iget-object v5, p0, Landroid/support/constraint/solver/widgets/ConstraintWidgetContainer;->mChildren:Ljava/util/ArrayList;

    invoke-virtual {v5, v2}, Ljava/util/ArrayList;->get(I)Ljava/lang/Object;

    move-result-object v4

    check-cast v4, Landroid/support/constraint/solver/widgets/ConstraintWidget;

    .line 167
    .local v4, "widget":Landroid/support/constraint/solver/widgets/ConstraintWidget;
    instance-of v5, v4, Landroid/support/constraint/solver/widgets/ConstraintWidgetContainer;

    if-eqz v5, :cond_4

    .line 168
    iget-object v1, v4, Landroid/support/constraint/solver/widgets/ConstraintWidget;->mHorizontalDimensionBehaviour:Landroid/support/constraint/solver/widgets/ConstraintWidget$DimensionBehaviour;

    .line 169
    .local v1, "horizontalBehaviour":Landroid/support/constraint/solver/widgets/ConstraintWidget$DimensionBehaviour;
    iget-object v3, v4, Landroid/support/constraint/solver/widgets/ConstraintWidget;->mVerticalDimensionBehaviour:Landroid/support/constraint/solver/widgets/ConstraintWidget$DimensionBehaviour;

    .line 170
    .local v3, "verticalBehaviour":Landroid/support/constraint/solver/widgets/ConstraintWidget$DimensionBehaviour;
    sget-object v5, Landroid/support/constraint/solver/widgets/ConstraintWidget$DimensionBehaviour;->WRAP_CONTENT:Landroid/support/constraint/solver/widgets/ConstraintWidget$DimensionBehaviour;

    if-ne v1, v5, :cond_0

    .line 171
    sget-object v5, Landroid/support/constraint/solver/widgets/ConstraintWidget$DimensionBehaviour;->FIXED:Landroid/support/constraint/solver/widgets/ConstraintWidget$DimensionBehaviour;

    invoke-virtual {v4, v5}, Landroid/support/constraint/solver/widgets/ConstraintWidget;->setHorizontalDimensionBehaviour(Landroid/support/constraint/solver/widgets/ConstraintWidget$DimensionBehaviour;)V

    .line 173
    :cond_0
    sget-object v5, Landroid/support/constraint/solver/widgets/ConstraintWidget$DimensionBehaviour;->WRAP_CONTENT:Landroid/support/constraint/solver/widgets/ConstraintWidget$DimensionBehaviour;

    if-ne v3, v5, :cond_1

    .line 174
    sget-object v5, Landroid/support/constraint/solver/widgets/ConstraintWidget$DimensionBehaviour;->FIXED:Landroid/support/constraint/solver/widgets/ConstraintWidget$DimensionBehaviour;

    invoke-virtual {v4, v5}, Landroid/support/constraint/solver/widgets/ConstraintWidget;->setVerticalDimensionBehaviour(Landroid/support/constraint/solver/widgets/ConstraintWidget$DimensionBehaviour;)V

    .line 176
    :cond_1
    invoke-virtual {v4, p1, p2}, Landroid/support/constraint/solver/widgets/ConstraintWidget;->addToSolver(Landroid/support/constraint/solver/LinearSystem;I)V

    .line 177
    sget-object v5, Landroid/support/constraint/solver/widgets/ConstraintWidget$DimensionBehaviour;->WRAP_CONTENT:Landroid/support/constraint/solver/widgets/ConstraintWidget$DimensionBehaviour;

    if-ne v1, v5, :cond_2

    .line 178
    invoke-virtual {v4, v1}, Landroid/support/constraint/solver/widgets/ConstraintWidget;->setHorizontalDimensionBehaviour(Landroid/support/constraint/solver/widgets/ConstraintWidget$DimensionBehaviour;)V

    .line 180
    :cond_2
    sget-object v5, Landroid/support/constraint/solver/widgets/ConstraintWidget$DimensionBehaviour;->WRAP_CONTENT:Landroid/support/constraint/solver/widgets/ConstraintWidget$DimensionBehaviour;

    if-ne v3, v5, :cond_3

    .line 181
    invoke-virtual {v4, v3}, Landroid/support/constraint/solver/widgets/ConstraintWidget;->setVerticalDimensionBehaviour(Landroid/support/constraint/solver/widgets/ConstraintWidget$DimensionBehaviour;)V

    .line 165
    .end local v1    # "horizontalBehaviour":Landroid/support/constraint/solver/widgets/ConstraintWidget$DimensionBehaviour;
    .end local v3    # "verticalBehaviour":Landroid/support/constraint/solver/widgets/ConstraintWidget$DimensionBehaviour;
    :cond_3
    :goto_1
    add-int/lit8 v2, v2, 0x1

    goto :goto_0

    .line 184
    :cond_4
    invoke-virtual {v4, p1, p2}, Landroid/support/constraint/solver/widgets/ConstraintWidget;->addToSolver(Landroid/support/constraint/solver/LinearSystem;I)V

    goto :goto_1

    .line 187
    .end local v4    # "widget":Landroid/support/constraint/solver/widgets/ConstraintWidget;
    :cond_5
    iget v5, p0, Landroid/support/constraint/solver/widgets/ConstraintWidgetContainer;->mHorizontalChainsSize:I

    if-lez v5, :cond_6

    .line 188
    invoke-direct {p0, p1}, Landroid/support/constraint/solver/widgets/ConstraintWidgetContainer;->applyHorizontalChain(Landroid/support/constraint/solver/LinearSystem;)V

    .line 190
    :cond_6
    iget v5, p0, Landroid/support/constraint/solver/widgets/ConstraintWidgetContainer;->mVerticalChainsSize:I

    if-lez v5, :cond_7

    .line 191
    invoke-direct {p0, p1}, Landroid/support/constraint/solver/widgets/ConstraintWidgetContainer;->applyVerticalChain(Landroid/support/constraint/solver/LinearSystem;)V

    .line 193
    :cond_7
    return-void
.end method

.method public findWrapRecursive(Landroid/support/constraint/solver/widgets/ConstraintWidget;)V
    .locals 13
    .param p1, "widget"    # Landroid/support/constraint/solver/widgets/ConstraintWidget;

    .prologue
    .line 820
    invoke-virtual {p1}, Landroid/support/constraint/solver/widgets/ConstraintWidget;->getWrapWidth()I

    move-result v10

    .line 822
    .local v10, "w":I
    move v4, v10

    .line 823
    .local v4, "distToRight":I
    move v3, v10

    .line 824
    .local v3, "distToLeft":I
    const/4 v7, 0x0

    .line 825
    .local v7, "leftWidget":Landroid/support/constraint/solver/widgets/ConstraintWidget;
    const/4 v8, 0x0

    .line 827
    .local v8, "rightWidget":Landroid/support/constraint/solver/widgets/ConstraintWidget;
    const/4 v11, 0x1

    iput-boolean v11, p1, Landroid/support/constraint/solver/widgets/ConstraintWidget;->mVisited:Z

    .line 828
    iget-object v11, p1, Landroid/support/constraint/solver/widgets/ConstraintWidget;->mRight:Landroid/support/constraint/solver/widgets/ConstraintAnchor;

    invoke-virtual {v11}, Landroid/support/constraint/solver/widgets/ConstraintAnchor;->isConnected()Z

    move-result v11

    if-nez v11, :cond_2

    iget-object v11, p1, Landroid/support/constraint/solver/widgets/ConstraintWidget;->mLeft:Landroid/support/constraint/solver/widgets/ConstraintAnchor;

    invoke-virtual {v11}, Landroid/support/constraint/solver/widgets/ConstraintAnchor;->isConnected()Z

    move-result v11

    if-nez v11, :cond_2

    .line 829
    invoke-virtual {p1}, Landroid/support/constraint/solver/widgets/ConstraintWidget;->getX()I

    move-result v11

    add-int/2addr v3, v11

    .line 862
    :cond_0
    :goto_0
    iput v3, p1, Landroid/support/constraint/solver/widgets/ConstraintWidget;->mDistToLeft:I

    .line 863
    iput v4, p1, Landroid/support/constraint/solver/widgets/ConstraintWidget;->mDistToRight:I

    .line 866
    invoke-virtual {p1}, Landroid/support/constraint/solver/widgets/ConstraintWidget;->getWrapHeight()I

    move-result v6

    .line 867
    .local v6, "h":I
    move v5, v6

    .line 868
    .local v5, "distToTop":I
    move v2, v6

    .line 869
    .local v2, "distToBottom":I
    const/4 v9, 0x0

    .line 870
    .local v9, "topWidget":Landroid/support/constraint/solver/widgets/ConstraintWidget;
    iget-object v11, p1, Landroid/support/constraint/solver/widgets/ConstraintWidget;->mBaseline:Landroid/support/constraint/solver/widgets/ConstraintAnchor;

    iget-object v11, v11, Landroid/support/constraint/solver/widgets/ConstraintAnchor;->mTarget:Landroid/support/constraint/solver/widgets/ConstraintAnchor;

    if-nez v11, :cond_8

    iget-object v11, p1, Landroid/support/constraint/solver/widgets/ConstraintWidget;->mTop:Landroid/support/constraint/solver/widgets/ConstraintAnchor;

    iget-object v11, v11, Landroid/support/constraint/solver/widgets/ConstraintAnchor;->mTarget:Landroid/support/constraint/solver/widgets/ConstraintAnchor;

    if-nez v11, :cond_8

    iget-object v11, p1, Landroid/support/constraint/solver/widgets/ConstraintWidget;->mBottom:Landroid/support/constraint/solver/widgets/ConstraintAnchor;

    iget-object v11, v11, Landroid/support/constraint/solver/widgets/ConstraintAnchor;->mTarget:Landroid/support/constraint/solver/widgets/ConstraintAnchor;

    if-nez v11, :cond_8

    .line 871
    invoke-virtual {p1}, Landroid/support/constraint/solver/widgets/ConstraintWidget;->getY()I

    move-result v11

    add-int/2addr v5, v11

    .line 924
    :cond_1
    :goto_1
    iput v5, p1, Landroid/support/constraint/solver/widgets/ConstraintWidget;->mDistToTop:I

    .line 925
    iput v2, p1, Landroid/support/constraint/solver/widgets/ConstraintWidget;->mDistToBottom:I

    .line 926
    :goto_2
    return-void

    .line 831
    .end local v2    # "distToBottom":I
    .end local v5    # "distToTop":I
    .end local v6    # "h":I
    .end local v9    # "topWidget":Landroid/support/constraint/solver/widgets/ConstraintWidget;
    :cond_2
    iget-object v11, p1, Landroid/support/constraint/solver/widgets/ConstraintWidget;->mRight:Landroid/support/constraint/solver/widgets/ConstraintAnchor;

    iget-object v11, v11, Landroid/support/constraint/solver/widgets/ConstraintAnchor;->mTarget:Landroid/support/constraint/solver/widgets/ConstraintAnchor;

    if-eqz v11, :cond_3

    .line 832
    iget-object v11, p1, Landroid/support/constraint/solver/widgets/ConstraintWidget;->mRight:Landroid/support/constraint/solver/widgets/ConstraintAnchor;

    iget-object v11, v11, Landroid/support/constraint/solver/widgets/ConstraintAnchor;->mTarget:Landroid/support/constraint/solver/widgets/ConstraintAnchor;

    invoke-virtual {v11}, Landroid/support/constraint/solver/widgets/ConstraintAnchor;->getOwner()Landroid/support/constraint/solver/widgets/ConstraintWidget;

    move-result-object v8

    .line 833
    iget-object v11, p1, Landroid/support/constraint/solver/widgets/ConstraintWidget;->mRight:Landroid/support/constraint/solver/widgets/ConstraintAnchor;

    invoke-virtual {v11}, Landroid/support/constraint/solver/widgets/ConstraintAnchor;->getMargin()I

    move-result v11

    add-int/2addr v4, v11

    .line 834
    invoke-virtual {v8}, Landroid/support/constraint/solver/widgets/ConstraintWidget;->isRoot()Z

    move-result v11

    if-nez v11, :cond_3

    iget-boolean v11, v8, Landroid/support/constraint/solver/widgets/ConstraintWidget;->mVisited:Z

    if-nez v11, :cond_3

    .line 835
    invoke-virtual {p0, v8}, Landroid/support/constraint/solver/widgets/ConstraintWidgetContainer;->findWrapRecursive(Landroid/support/constraint/solver/widgets/ConstraintWidget;)V

    .line 838
    :cond_3
    iget-object v11, p1, Landroid/support/constraint/solver/widgets/ConstraintWidget;->mLeft:Landroid/support/constraint/solver/widgets/ConstraintAnchor;

    invoke-virtual {v11}, Landroid/support/constraint/solver/widgets/ConstraintAnchor;->isConnected()Z

    move-result v11

    if-eqz v11, :cond_4

    .line 839
    iget-object v11, p1, Landroid/support/constraint/solver/widgets/ConstraintWidget;->mLeft:Landroid/support/constraint/solver/widgets/ConstraintAnchor;

    iget-object v11, v11, Landroid/support/constraint/solver/widgets/ConstraintAnchor;->mTarget:Landroid/support/constraint/solver/widgets/ConstraintAnchor;

    invoke-virtual {v11}, Landroid/support/constraint/solver/widgets/ConstraintAnchor;->getOwner()Landroid/support/constraint/solver/widgets/ConstraintWidget;

    move-result-object v7

    .line 840
    iget-object v11, p1, Landroid/support/constraint/solver/widgets/ConstraintWidget;->mLeft:Landroid/support/constraint/solver/widgets/ConstraintAnchor;

    invoke-virtual {v11}, Landroid/support/constraint/solver/widgets/ConstraintAnchor;->getMargin()I

    move-result v11

    add-int/2addr v3, v11

    .line 841
    invoke-virtual {v7}, Landroid/support/constraint/solver/widgets/ConstraintWidget;->isRoot()Z

    move-result v11

    if-nez v11, :cond_4

    iget-boolean v11, v7, Landroid/support/constraint/solver/widgets/ConstraintWidget;->mVisited:Z

    if-nez v11, :cond_4

    .line 842
    invoke-virtual {p0, v7}, Landroid/support/constraint/solver/widgets/ConstraintWidgetContainer;->findWrapRecursive(Landroid/support/constraint/solver/widgets/ConstraintWidget;)V

    .line 846
    :cond_4
    iget-object v11, p1, Landroid/support/constraint/solver/widgets/ConstraintWidget;->mRight:Landroid/support/constraint/solver/widgets/ConstraintAnchor;

    iget-object v11, v11, Landroid/support/constraint/solver/widgets/ConstraintAnchor;->mTarget:Landroid/support/constraint/solver/widgets/ConstraintAnchor;

    if-eqz v11, :cond_5

    invoke-virtual {v8}, Landroid/support/constraint/solver/widgets/ConstraintWidget;->isRoot()Z

    move-result v11

    if-nez v11, :cond_5

    .line 847
    iget-object v11, p1, Landroid/support/constraint/solver/widgets/ConstraintWidget;->mRight:Landroid/support/constraint/solver/widgets/ConstraintAnchor;

    iget-object v11, v11, Landroid/support/constraint/solver/widgets/ConstraintAnchor;->mTarget:Landroid/support/constraint/solver/widgets/ConstraintAnchor;

    iget-object v11, v11, Landroid/support/constraint/solver/widgets/ConstraintAnchor;->mType:Landroid/support/constraint/solver/widgets/ConstraintAnchor$Type;

    sget-object v12, Landroid/support/constraint/solver/widgets/ConstraintAnchor$Type;->RIGHT:Landroid/support/constraint/solver/widgets/ConstraintAnchor$Type;

    if-ne v11, v12, :cond_6

    .line 848
    iget v11, v8, Landroid/support/constraint/solver/widgets/ConstraintWidget;->mDistToRight:I

    invoke-virtual {v8}, Landroid/support/constraint/solver/widgets/ConstraintWidget;->getWrapWidth()I

    move-result v12

    sub-int/2addr v11, v12

    add-int/2addr v4, v11

    .line 854
    :cond_5
    :goto_3
    iget-object v11, p1, Landroid/support/constraint/solver/widgets/ConstraintWidget;->mLeft:Landroid/support/constraint/solver/widgets/ConstraintAnchor;

    iget-object v11, v11, Landroid/support/constraint/solver/widgets/ConstraintAnchor;->mTarget:Landroid/support/constraint/solver/widgets/ConstraintAnchor;

    if-eqz v11, :cond_0

    invoke-virtual {v7}, Landroid/support/constraint/solver/widgets/ConstraintWidget;->isRoot()Z

    move-result v11

    if-nez v11, :cond_0

    .line 855
    iget-object v11, p1, Landroid/support/constraint/solver/widgets/ConstraintWidget;->mLeft:Landroid/support/constraint/solver/widgets/ConstraintAnchor;

    iget-object v11, v11, Landroid/support/constraint/solver/widgets/ConstraintAnchor;->mTarget:Landroid/support/constraint/solver/widgets/ConstraintAnchor;

    invoke-virtual {v11}, Landroid/support/constraint/solver/widgets/ConstraintAnchor;->getType()Landroid/support/constraint/solver/widgets/ConstraintAnchor$Type;

    move-result-object v11

    sget-object v12, Landroid/support/constraint/solver/widgets/ConstraintAnchor$Type;->LEFT:Landroid/support/constraint/solver/widgets/ConstraintAnchor$Type;

    if-ne v11, v12, :cond_7

    .line 856
    iget v11, v7, Landroid/support/constraint/solver/widgets/ConstraintWidget;->mDistToLeft:I

    invoke-virtual {v7}, Landroid/support/constraint/solver/widgets/ConstraintWidget;->getWrapWidth()I

    move-result v12

    sub-int/2addr v11, v12

    add-int/2addr v3, v11

    goto/16 :goto_0

    .line 849
    :cond_6
    iget-object v11, p1, Landroid/support/constraint/solver/widgets/ConstraintWidget;->mRight:Landroid/support/constraint/solver/widgets/ConstraintAnchor;

    iget-object v11, v11, Landroid/support/constraint/solver/widgets/ConstraintAnchor;->mTarget:Landroid/support/constraint/solver/widgets/ConstraintAnchor;

    invoke-virtual {v11}, Landroid/support/constraint/solver/widgets/ConstraintAnchor;->getType()Landroid/support/constraint/solver/widgets/ConstraintAnchor$Type;

    move-result-object v11

    sget-object v12, Landroid/support/constraint/solver/widgets/ConstraintAnchor$Type;->LEFT:Landroid/support/constraint/solver/widgets/ConstraintAnchor$Type;

    if-ne v11, v12, :cond_5

    .line 850
    iget v11, v8, Landroid/support/constraint/solver/widgets/ConstraintWidget;->mDistToRight:I

    add-int/2addr v4, v11

    goto :goto_3

    .line 857
    :cond_7
    iget-object v11, p1, Landroid/support/constraint/solver/widgets/ConstraintWidget;->mLeft:Landroid/support/constraint/solver/widgets/ConstraintAnchor;

    iget-object v11, v11, Landroid/support/constraint/solver/widgets/ConstraintAnchor;->mTarget:Landroid/support/constraint/solver/widgets/ConstraintAnchor;

    invoke-virtual {v11}, Landroid/support/constraint/solver/widgets/ConstraintAnchor;->getType()Landroid/support/constraint/solver/widgets/ConstraintAnchor$Type;

    move-result-object v11

    sget-object v12, Landroid/support/constraint/solver/widgets/ConstraintAnchor$Type;->RIGHT:Landroid/support/constraint/solver/widgets/ConstraintAnchor$Type;

    if-ne v11, v12, :cond_0

    .line 858
    iget v11, v7, Landroid/support/constraint/solver/widgets/ConstraintWidget;->mDistToLeft:I

    add-int/2addr v3, v11

    goto/16 :goto_0

    .line 873
    .restart local v2    # "distToBottom":I
    .restart local v5    # "distToTop":I
    .restart local v6    # "h":I
    .restart local v9    # "topWidget":Landroid/support/constraint/solver/widgets/ConstraintWidget;
    :cond_8
    iget-object v11, p1, Landroid/support/constraint/solver/widgets/ConstraintWidget;->mBaseline:Landroid/support/constraint/solver/widgets/ConstraintAnchor;

    invoke-virtual {v11}, Landroid/support/constraint/solver/widgets/ConstraintAnchor;->isConnected()Z

    move-result v11

    if-eqz v11, :cond_c

    .line 874
    iget-object v11, p1, Landroid/support/constraint/solver/widgets/ConstraintWidget;->mBaseline:Landroid/support/constraint/solver/widgets/ConstraintAnchor;

    iget-object v11, v11, Landroid/support/constraint/solver/widgets/ConstraintAnchor;->mTarget:Landroid/support/constraint/solver/widgets/ConstraintAnchor;

    invoke-virtual {v11}, Landroid/support/constraint/solver/widgets/ConstraintAnchor;->getOwner()Landroid/support/constraint/solver/widgets/ConstraintWidget;

    move-result-object v0

    .line 875
    .local v0, "baseLineWidget":Landroid/support/constraint/solver/widgets/ConstraintWidget;
    iget-boolean v11, v0, Landroid/support/constraint/solver/widgets/ConstraintWidget;->mVisited:Z

    if-nez v11, :cond_9

    .line 876
    invoke-virtual {p0, v0}, Landroid/support/constraint/solver/widgets/ConstraintWidgetContainer;->findWrapRecursive(Landroid/support/constraint/solver/widgets/ConstraintWidget;)V

    .line 878
    :cond_9
    iget v11, v0, Landroid/support/constraint/solver/widgets/ConstraintWidget;->mDistToBottom:I

    if-le v11, v2, :cond_a

    .line 879
    iget v2, v0, Landroid/support/constraint/solver/widgets/ConstraintWidget;->mDistToBottom:I

    .line 881
    :cond_a
    iget v11, v0, Landroid/support/constraint/solver/widgets/ConstraintWidget;->mDistToTop:I

    if-le v11, v5, :cond_b

    .line 882
    iget v5, v0, Landroid/support/constraint/solver/widgets/ConstraintWidget;->mDistToTop:I

    .line 884
    :cond_b
    iput v5, p1, Landroid/support/constraint/solver/widgets/ConstraintWidget;->mDistToTop:I

    .line 885
    iput v2, p1, Landroid/support/constraint/solver/widgets/ConstraintWidget;->mDistToBottom:I

    goto/16 :goto_2

    .line 889
    .end local v0    # "baseLineWidget":Landroid/support/constraint/solver/widgets/ConstraintWidget;
    :cond_c
    iget-object v11, p1, Landroid/support/constraint/solver/widgets/ConstraintWidget;->mTop:Landroid/support/constraint/solver/widgets/ConstraintAnchor;

    invoke-virtual {v11}, Landroid/support/constraint/solver/widgets/ConstraintAnchor;->isConnected()Z

    move-result v11

    if-eqz v11, :cond_d

    .line 890
    iget-object v11, p1, Landroid/support/constraint/solver/widgets/ConstraintWidget;->mTop:Landroid/support/constraint/solver/widgets/ConstraintAnchor;

    iget-object v11, v11, Landroid/support/constraint/solver/widgets/ConstraintAnchor;->mTarget:Landroid/support/constraint/solver/widgets/ConstraintAnchor;

    invoke-virtual {v11}, Landroid/support/constraint/solver/widgets/ConstraintAnchor;->getOwner()Landroid/support/constraint/solver/widgets/ConstraintWidget;

    move-result-object v9

    .line 891
    iget-object v11, p1, Landroid/support/constraint/solver/widgets/ConstraintWidget;->mTop:Landroid/support/constraint/solver/widgets/ConstraintAnchor;

    invoke-virtual {v11}, Landroid/support/constraint/solver/widgets/ConstraintAnchor;->getMargin()I

    move-result v11

    add-int/2addr v5, v11

    .line 892
    invoke-virtual {v9}, Landroid/support/constraint/solver/widgets/ConstraintWidget;->isRoot()Z

    move-result v11

    if-nez v11, :cond_d

    iget-boolean v11, v9, Landroid/support/constraint/solver/widgets/ConstraintWidget;->mVisited:Z

    if-nez v11, :cond_d

    .line 893
    invoke-virtual {p0, v9}, Landroid/support/constraint/solver/widgets/ConstraintWidgetContainer;->findWrapRecursive(Landroid/support/constraint/solver/widgets/ConstraintWidget;)V

    .line 897
    :cond_d
    const/4 v1, 0x0

    .line 898
    .local v1, "bottomWidget":Landroid/support/constraint/solver/widgets/ConstraintWidget;
    iget-object v11, p1, Landroid/support/constraint/solver/widgets/ConstraintWidget;->mBottom:Landroid/support/constraint/solver/widgets/ConstraintAnchor;

    invoke-virtual {v11}, Landroid/support/constraint/solver/widgets/ConstraintAnchor;->isConnected()Z

    move-result v11

    if-eqz v11, :cond_e

    .line 899
    iget-object v11, p1, Landroid/support/constraint/solver/widgets/ConstraintWidget;->mBottom:Landroid/support/constraint/solver/widgets/ConstraintAnchor;

    iget-object v11, v11, Landroid/support/constraint/solver/widgets/ConstraintAnchor;->mTarget:Landroid/support/constraint/solver/widgets/ConstraintAnchor;

    invoke-virtual {v11}, Landroid/support/constraint/solver/widgets/ConstraintAnchor;->getOwner()Landroid/support/constraint/solver/widgets/ConstraintWidget;

    move-result-object v1

    .line 900
    iget-object v11, p1, Landroid/support/constraint/solver/widgets/ConstraintWidget;->mBottom:Landroid/support/constraint/solver/widgets/ConstraintAnchor;

    invoke-virtual {v11}, Landroid/support/constraint/solver/widgets/ConstraintAnchor;->getMargin()I

    move-result v11

    add-int/2addr v2, v11

    .line 901
    invoke-virtual {v1}, Landroid/support/constraint/solver/widgets/ConstraintWidget;->isRoot()Z

    move-result v11

    if-nez v11, :cond_e

    iget-boolean v11, v1, Landroid/support/constraint/solver/widgets/ConstraintWidget;->mVisited:Z

    if-nez v11, :cond_e

    .line 902
    invoke-virtual {p0, v1}, Landroid/support/constraint/solver/widgets/ConstraintWidgetContainer;->findWrapRecursive(Landroid/support/constraint/solver/widgets/ConstraintWidget;)V

    .line 909
    :cond_e
    iget-object v11, p1, Landroid/support/constraint/solver/widgets/ConstraintWidget;->mTop:Landroid/support/constraint/solver/widgets/ConstraintAnchor;

    iget-object v11, v11, Landroid/support/constraint/solver/widgets/ConstraintAnchor;->mTarget:Landroid/support/constraint/solver/widgets/ConstraintAnchor;

    if-eqz v11, :cond_f

    invoke-virtual {v9}, Landroid/support/constraint/solver/widgets/ConstraintWidget;->isRoot()Z

    move-result v11

    if-nez v11, :cond_f

    .line 910
    iget-object v11, p1, Landroid/support/constraint/solver/widgets/ConstraintWidget;->mTop:Landroid/support/constraint/solver/widgets/ConstraintAnchor;

    iget-object v11, v11, Landroid/support/constraint/solver/widgets/ConstraintAnchor;->mTarget:Landroid/support/constraint/solver/widgets/ConstraintAnchor;

    invoke-virtual {v11}, Landroid/support/constraint/solver/widgets/ConstraintAnchor;->getType()Landroid/support/constraint/solver/widgets/ConstraintAnchor$Type;

    move-result-object v11

    sget-object v12, Landroid/support/constraint/solver/widgets/ConstraintAnchor$Type;->TOP:Landroid/support/constraint/solver/widgets/ConstraintAnchor$Type;

    if-ne v11, v12, :cond_10

    .line 911
    iget v11, v9, Landroid/support/constraint/solver/widgets/ConstraintWidget;->mDistToTop:I

    invoke-virtual {v9}, Landroid/support/constraint/solver/widgets/ConstraintWidget;->getWrapHeight()I

    move-result v12

    sub-int/2addr v11, v12

    add-int/2addr v5, v11

    .line 916
    :cond_f
    :goto_4
    iget-object v11, p1, Landroid/support/constraint/solver/widgets/ConstraintWidget;->mBottom:Landroid/support/constraint/solver/widgets/ConstraintAnchor;

    iget-object v11, v11, Landroid/support/constraint/solver/widgets/ConstraintAnchor;->mTarget:Landroid/support/constraint/solver/widgets/ConstraintAnchor;

    if-eqz v11, :cond_1

    invoke-virtual {v1}, Landroid/support/constraint/solver/widgets/ConstraintWidget;->isRoot()Z

    move-result v11

    if-nez v11, :cond_1

    .line 917
    iget-object v11, p1, Landroid/support/constraint/solver/widgets/ConstraintWidget;->mBottom:Landroid/support/constraint/solver/widgets/ConstraintAnchor;

    iget-object v11, v11, Landroid/support/constraint/solver/widgets/ConstraintAnchor;->mTarget:Landroid/support/constraint/solver/widgets/ConstraintAnchor;

    invoke-virtual {v11}, Landroid/support/constraint/solver/widgets/ConstraintAnchor;->getType()Landroid/support/constraint/solver/widgets/ConstraintAnchor$Type;

    move-result-object v11

    sget-object v12, Landroid/support/constraint/solver/widgets/ConstraintAnchor$Type;->BOTTOM:Landroid/support/constraint/solver/widgets/ConstraintAnchor$Type;

    if-ne v11, v12, :cond_11

    .line 918
    iget v11, v1, Landroid/support/constraint/solver/widgets/ConstraintWidget;->mDistToBottom:I

    invoke-virtual {v1}, Landroid/support/constraint/solver/widgets/ConstraintWidget;->getWrapHeight()I

    move-result v12

    sub-int/2addr v11, v12

    add-int/2addr v2, v11

    goto/16 :goto_1

    .line 912
    :cond_10
    iget-object v11, p1, Landroid/support/constraint/solver/widgets/ConstraintWidget;->mTop:Landroid/support/constraint/solver/widgets/ConstraintAnchor;

    iget-object v11, v11, Landroid/support/constraint/solver/widgets/ConstraintAnchor;->mTarget:Landroid/support/constraint/solver/widgets/ConstraintAnchor;

    invoke-virtual {v11}, Landroid/support/constraint/solver/widgets/ConstraintAnchor;->getType()Landroid/support/constraint/solver/widgets/ConstraintAnchor$Type;

    move-result-object v11

    sget-object v12, Landroid/support/constraint/solver/widgets/ConstraintAnchor$Type;->BOTTOM:Landroid/support/constraint/solver/widgets/ConstraintAnchor$Type;

    if-ne v11, v12, :cond_f

    .line 913
    iget v11, v9, Landroid/support/constraint/solver/widgets/ConstraintWidget;->mDistToTop:I

    add-int/2addr v5, v11

    goto :goto_4

    .line 919
    :cond_11
    iget-object v11, p1, Landroid/support/constraint/solver/widgets/ConstraintWidget;->mBottom:Landroid/support/constraint/solver/widgets/ConstraintAnchor;

    iget-object v11, v11, Landroid/support/constraint/solver/widgets/ConstraintAnchor;->mTarget:Landroid/support/constraint/solver/widgets/ConstraintAnchor;

    invoke-virtual {v11}, Landroid/support/constraint/solver/widgets/ConstraintAnchor;->getType()Landroid/support/constraint/solver/widgets/ConstraintAnchor$Type;

    move-result-object v11

    sget-object v12, Landroid/support/constraint/solver/widgets/ConstraintAnchor$Type;->TOP:Landroid/support/constraint/solver/widgets/ConstraintAnchor$Type;

    if-ne v11, v12, :cond_1

    .line 920
    iget v11, v1, Landroid/support/constraint/solver/widgets/ConstraintWidget;->mDistToBottom:I

    add-int/2addr v2, v11

    goto/16 :goto_1
.end method

.method public findWrapSize(Ljava/util/ArrayList;)V
    .locals 14
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "(",
            "Ljava/util/ArrayList",
            "<",
            "Landroid/support/constraint/solver/widgets/ConstraintWidget;",
            ">;)V"
        }
    .end annotation

    .prologue
    .line 934
    .local p1, "children":Ljava/util/ArrayList;, "Ljava/util/ArrayList<Landroid/support/constraint/solver/widgets/ConstraintWidget;>;"
    const/4 v9, 0x0

    .line 935
    .local v9, "maxTopDist":I
    const/4 v7, 0x0

    .line 936
    .local v7, "maxLeftDist":I
    const/4 v8, 0x0

    .line 937
    .local v8, "maxRightDist":I
    const/4 v4, 0x0

    .line 939
    .local v4, "maxBottomDist":I
    const/4 v6, 0x0

    .line 940
    .local v6, "maxConnectWidth":I
    const/4 v5, 0x0

    .line 941
    .local v5, "maxConnectHeight":I
    invoke-virtual {p1}, Ljava/util/ArrayList;->size()I

    move-result v10

    .line 942
    .local v10, "size":I
    const/4 v2, 0x0

    .local v2, "j":I
    :goto_0
    if-ge v2, v10, :cond_2

    .line 943
    invoke-virtual {p1, v2}, Ljava/util/ArrayList;->get(I)Ljava/lang/Object;

    move-result-object v11

    check-cast v11, Landroid/support/constraint/solver/widgets/ConstraintWidget;

    .line 944
    .local v11, "widget":Landroid/support/constraint/solver/widgets/ConstraintWidget;
    invoke-virtual {v11}, Landroid/support/constraint/solver/widgets/ConstraintWidget;->isRoot()Z

    move-result v12

    if-eqz v12, :cond_0

    .line 942
    :goto_1
    add-int/lit8 v2, v2, 0x1

    goto :goto_0

    .line 947
    :cond_0
    iget-boolean v12, v11, Landroid/support/constraint/solver/widgets/ConstraintWidget;->mVisited:Z

    if-nez v12, :cond_1

    .line 948
    invoke-virtual {p0, v11}, Landroid/support/constraint/solver/widgets/ConstraintWidgetContainer;->findWrapRecursive(Landroid/support/constraint/solver/widgets/ConstraintWidget;)V

    .line 950
    :cond_1
    iget v12, v11, Landroid/support/constraint/solver/widgets/ConstraintWidget;->mDistToLeft:I

    iget v13, v11, Landroid/support/constraint/solver/widgets/ConstraintWidget;->mDistToRight:I

    add-int/2addr v12, v13

    invoke-virtual {v11}, Landroid/support/constraint/solver/widgets/ConstraintWidget;->getWrapWidth()I

    move-result v13

    sub-int v1, v12, v13

    .line 951
    .local v1, "connectWidth":I
    iget v12, v11, Landroid/support/constraint/solver/widgets/ConstraintWidget;->mDistToTop:I

    iget v13, v11, Landroid/support/constraint/solver/widgets/ConstraintWidget;->mDistToBottom:I

    add-int/2addr v12, v13

    invoke-virtual {v11}, Landroid/support/constraint/solver/widgets/ConstraintWidget;->getWrapHeight()I

    move-result v13

    sub-int v0, v12, v13

    .line 952
    .local v0, "connectHeight":I
    iget v12, v11, Landroid/support/constraint/solver/widgets/ConstraintWidget;->mDistToLeft:I

    invoke-static {v7, v12}, Ljava/lang/Math;->max(II)I

    move-result v7

    .line 953
    iget v12, v11, Landroid/support/constraint/solver/widgets/ConstraintWidget;->mDistToRight:I

    invoke-static {v8, v12}, Ljava/lang/Math;->max(II)I

    move-result v8

    .line 954
    iget v12, v11, Landroid/support/constraint/solver/widgets/ConstraintWidget;->mDistToBottom:I

    invoke-static {v4, v12}, Ljava/lang/Math;->max(II)I

    move-result v4

    .line 955
    iget v12, v11, Landroid/support/constraint/solver/widgets/ConstraintWidget;->mDistToTop:I

    invoke-static {v9, v12}, Ljava/lang/Math;->max(II)I

    move-result v9

    .line 956
    invoke-static {v6, v1}, Ljava/lang/Math;->max(II)I

    move-result v6

    .line 957
    invoke-static {v5, v0}, Ljava/lang/Math;->max(II)I

    move-result v5

    goto :goto_1

    .line 959
    .end local v0    # "connectHeight":I
    .end local v1    # "connectWidth":I
    .end local v11    # "widget":Landroid/support/constraint/solver/widgets/ConstraintWidget;
    :cond_2
    invoke-static {v7, v8}, Ljava/lang/Math;->max(II)I

    move-result v3

    .line 960
    .local v3, "max":I
    invoke-static {v3, v6}, Ljava/lang/Math;->max(II)I

    move-result v12

    iput v12, p0, Landroid/support/constraint/solver/widgets/ConstraintWidgetContainer;->mWrapWidth:I

    .line 961
    invoke-static {v9, v4}, Ljava/lang/Math;->max(II)I

    move-result v3

    .line 962
    invoke-static {v3, v5}, Ljava/lang/Math;->max(II)I

    move-result v12

    iput v12, p0, Landroid/support/constraint/solver/widgets/ConstraintWidgetContainer;->mWrapHeight:I

    .line 964
    const/4 v2, 0x0

    :goto_2
    if-ge v2, v10, :cond_3

    .line 965
    invoke-virtual {p1, v2}, Ljava/util/ArrayList;->get(I)Ljava/lang/Object;

    move-result-object v12

    check-cast v12, Landroid/support/constraint/solver/widgets/ConstraintWidget;

    const/4 v13, 0x0

    iput-boolean v13, v12, Landroid/support/constraint/solver/widgets/ConstraintWidget;->mVisited:Z

    .line 964
    add-int/lit8 v2, v2, 0x1

    goto :goto_2

    .line 967
    :cond_3
    return-void
.end method

.method public getHorizontalGuidelines()Ljava/util/ArrayList;
    .locals 6
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "()",
            "Ljava/util/ArrayList",
            "<",
            "Landroid/support/constraint/solver/widgets/Guideline;",
            ">;"
        }
    .end annotation

    .prologue
    .line 1299
    new-instance v1, Ljava/util/ArrayList;

    invoke-direct {v1}, Ljava/util/ArrayList;-><init>()V

    .line 1300
    .local v1, "guidelines":Ljava/util/ArrayList;, "Ljava/util/ArrayList<Landroid/support/constraint/solver/widgets/Guideline;>;"
    const/4 v2, 0x0

    .local v2, "i":I
    iget-object v5, p0, Landroid/support/constraint/solver/widgets/ConstraintWidgetContainer;->mChildren:Ljava/util/ArrayList;

    invoke-virtual {v5}, Ljava/util/ArrayList;->size()I

    move-result v3

    .local v3, "mChildrenSize":I
    :goto_0
    if-ge v2, v3, :cond_1

    .line 1301
    iget-object v5, p0, Landroid/support/constraint/solver/widgets/ConstraintWidgetContainer;->mChildren:Ljava/util/ArrayList;

    invoke-virtual {v5, v2}, Ljava/util/ArrayList;->get(I)Ljava/lang/Object;

    move-result-object v4

    check-cast v4, Landroid/support/constraint/solver/widgets/ConstraintWidget;

    .line 1302
    .local v4, "widget":Landroid/support/constraint/solver/widgets/ConstraintWidget;
    instance-of v5, v4, Landroid/support/constraint/solver/widgets/Guideline;

    if-eqz v5, :cond_0

    move-object v0, v4

    .line 1303
    check-cast v0, Landroid/support/constraint/solver/widgets/Guideline;

    .line 1304
    .local v0, "guideline":Landroid/support/constraint/solver/widgets/Guideline;
    invoke-virtual {v0}, Landroid/support/constraint/solver/widgets/Guideline;->getOrientation()I

    move-result v5

    if-nez v5, :cond_0

    .line 1305
    invoke-virtual {v1, v0}, Ljava/util/ArrayList;->add(Ljava/lang/Object;)Z

    .line 1300
    .end local v0    # "guideline":Landroid/support/constraint/solver/widgets/Guideline;
    :cond_0
    add-int/lit8 v2, v2, 0x1

    goto :goto_0

    .line 1309
    .end local v4    # "widget":Landroid/support/constraint/solver/widgets/ConstraintWidget;
    :cond_1
    return-object v1
.end method

.method public getSystem()Landroid/support/constraint/solver/LinearSystem;
    .locals 1

    .prologue
    .line 1313
    iget-object v0, p0, Landroid/support/constraint/solver/widgets/ConstraintWidgetContainer;->mSystem:Landroid/support/constraint/solver/LinearSystem;

    return-object v0
.end method

.method public getType()Ljava/lang/String;
    .locals 1

    .prologue
    .line 96
    const-string v0, "ConstraintLayout"

    return-object v0
.end method

.method public getVerticalGuidelines()Ljava/util/ArrayList;
    .locals 7
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "()",
            "Ljava/util/ArrayList",
            "<",
            "Landroid/support/constraint/solver/widgets/Guideline;",
            ">;"
        }
    .end annotation

    .prologue
    .line 1280
    new-instance v1, Ljava/util/ArrayList;

    invoke-direct {v1}, Ljava/util/ArrayList;-><init>()V

    .line 1281
    .local v1, "guidelines":Ljava/util/ArrayList;, "Ljava/util/ArrayList<Landroid/support/constraint/solver/widgets/Guideline;>;"
    const/4 v2, 0x0

    .local v2, "i":I
    iget-object v5, p0, Landroid/support/constraint/solver/widgets/ConstraintWidgetContainer;->mChildren:Ljava/util/ArrayList;

    invoke-virtual {v5}, Ljava/util/ArrayList;->size()I

    move-result v3

    .local v3, "mChildrenSize":I
    :goto_0
    if-ge v2, v3, :cond_1

    .line 1282
    iget-object v5, p0, Landroid/support/constraint/solver/widgets/ConstraintWidgetContainer;->mChildren:Ljava/util/ArrayList;

    invoke-virtual {v5, v2}, Ljava/util/ArrayList;->get(I)Ljava/lang/Object;

    move-result-object v4

    check-cast v4, Landroid/support/constraint/solver/widgets/ConstraintWidget;

    .line 1283
    .local v4, "widget":Landroid/support/constraint/solver/widgets/ConstraintWidget;
    instance-of v5, v4, Landroid/support/constraint/solver/widgets/Guideline;

    if-eqz v5, :cond_0

    move-object v0, v4

    .line 1284
    check-cast v0, Landroid/support/constraint/solver/widgets/Guideline;

    .line 1285
    .local v0, "guideline":Landroid/support/constraint/solver/widgets/Guideline;
    invoke-virtual {v0}, Landroid/support/constraint/solver/widgets/Guideline;->getOrientation()I

    move-result v5

    const/4 v6, 0x1

    if-ne v5, v6, :cond_0

    .line 1286
    invoke-virtual {v1, v0}, Ljava/util/ArrayList;->add(Ljava/lang/Object;)Z

    .line 1281
    .end local v0    # "guideline":Landroid/support/constraint/solver/widgets/Guideline;
    :cond_0
    add-int/lit8 v2, v2, 0x1

    goto :goto_0

    .line 1290
    .end local v4    # "widget":Landroid/support/constraint/solver/widgets/ConstraintWidget;
    :cond_1
    return-object v1
.end method

.method public handlesInternalConstraints()Z
    .locals 1

    .prologue
    .line 1267
    const/4 v0, 0x0

    return v0
.end method

.method public isAnimating()Z
    .locals 5

    .prologue
    const/4 v3, 0x1

    .line 1249
    invoke-super {p0}, Landroid/support/constraint/solver/widgets/WidgetContainer;->isAnimating()Z

    move-result v4

    if-eqz v4, :cond_1

    .line 1258
    :cond_0
    :goto_0
    return v3

    .line 1252
    :cond_1
    const/4 v0, 0x0

    .local v0, "i":I
    iget-object v4, p0, Landroid/support/constraint/solver/widgets/ConstraintWidgetContainer;->mChildren:Ljava/util/ArrayList;

    invoke-virtual {v4}, Ljava/util/ArrayList;->size()I

    move-result v1

    .local v1, "mChildrenSize":I
    :goto_1
    if-ge v0, v1, :cond_2

    .line 1253
    iget-object v4, p0, Landroid/support/constraint/solver/widgets/ConstraintWidgetContainer;->mChildren:Ljava/util/ArrayList;

    invoke-virtual {v4, v0}, Ljava/util/ArrayList;->get(I)Ljava/lang/Object;

    move-result-object v2

    check-cast v2, Landroid/support/constraint/solver/widgets/ConstraintWidget;

    .line 1254
    .local v2, "widget":Landroid/support/constraint/solver/widgets/ConstraintWidget;
    invoke-virtual {v2}, Landroid/support/constraint/solver/widgets/ConstraintWidget;->isAnimating()Z

    move-result v4

    if-nez v4, :cond_0

    .line 1252
    add-int/lit8 v0, v0, 0x1

    goto :goto_1

    .line 1258
    .end local v2    # "widget":Landroid/support/constraint/solver/widgets/ConstraintWidget;
    :cond_2
    const/4 v3, 0x0

    goto :goto_0
.end method

.method public layout()V
    .locals 13

    .prologue
    const v12, 0x7fffffff

    const/4 v11, 0x0

    .line 699
    iget v6, p0, Landroid/support/constraint/solver/widgets/ConstraintWidgetContainer;->mX:I

    .line 700
    .local v6, "prex":I
    iget v7, p0, Landroid/support/constraint/solver/widgets/ConstraintWidgetContainer;->mY:I

    .line 701
    .local v7, "prey":I
    invoke-virtual {p0}, Landroid/support/constraint/solver/widgets/ConstraintWidgetContainer;->getWidth()I

    move-result v5

    .line 702
    .local v5, "prew":I
    invoke-virtual {p0}, Landroid/support/constraint/solver/widgets/ConstraintWidgetContainer;->getHeight()I

    move-result v4

    .line 703
    .local v4, "preh":I
    iget-object v10, p0, Landroid/support/constraint/solver/widgets/ConstraintWidgetContainer;->mParent:Landroid/support/constraint/solver/widgets/ConstraintWidget;

    if-eqz v10, :cond_2

    .line 704
    iget-object v10, p0, Landroid/support/constraint/solver/widgets/ConstraintWidgetContainer;->mSnapshot:Landroid/support/constraint/solver/widgets/Snapshot;

    if-nez v10, :cond_0

    .line 705
    new-instance v10, Landroid/support/constraint/solver/widgets/Snapshot;

    invoke-direct {v10, p0}, Landroid/support/constraint/solver/widgets/Snapshot;-><init>(Landroid/support/constraint/solver/widgets/ConstraintWidget;)V

    iput-object v10, p0, Landroid/support/constraint/solver/widgets/ConstraintWidgetContainer;->mSnapshot:Landroid/support/constraint/solver/widgets/Snapshot;

    .line 707
    :cond_0
    iget-object v10, p0, Landroid/support/constraint/solver/widgets/ConstraintWidgetContainer;->mSnapshot:Landroid/support/constraint/solver/widgets/Snapshot;

    invoke-virtual {v10, p0}, Landroid/support/constraint/solver/widgets/Snapshot;->updateFrom(Landroid/support/constraint/solver/widgets/ConstraintWidget;)V

    .line 712
    iget v10, p0, Landroid/support/constraint/solver/widgets/ConstraintWidgetContainer;->mPaddingLeft:I

    invoke-virtual {p0, v10}, Landroid/support/constraint/solver/widgets/ConstraintWidgetContainer;->setX(I)V

    .line 713
    iget v10, p0, Landroid/support/constraint/solver/widgets/ConstraintWidgetContainer;->mPaddingTop:I

    invoke-virtual {p0, v10}, Landroid/support/constraint/solver/widgets/ConstraintWidgetContainer;->setY(I)V

    .line 714
    invoke-virtual {p0}, Landroid/support/constraint/solver/widgets/ConstraintWidgetContainer;->resetAnchors()V

    .line 715
    iget-object v10, p0, Landroid/support/constraint/solver/widgets/ConstraintWidgetContainer;->mSystem:Landroid/support/constraint/solver/LinearSystem;

    invoke-virtual {v10}, Landroid/support/constraint/solver/LinearSystem;->getCache()Landroid/support/constraint/solver/Cache;

    move-result-object v10

    invoke-virtual {p0, v10}, Landroid/support/constraint/solver/widgets/ConstraintWidgetContainer;->resetSolverVariables(Landroid/support/constraint/solver/Cache;)V

    .line 722
    :goto_0
    invoke-direct {p0}, Landroid/support/constraint/solver/widgets/ConstraintWidgetContainer;->resetChains()V

    .line 726
    iget-object v10, p0, Landroid/support/constraint/solver/widgets/ConstraintWidgetContainer;->mChildren:Ljava/util/ArrayList;

    invoke-virtual {v10}, Ljava/util/ArrayList;->size()I

    move-result v0

    .line 727
    .local v0, "count":I
    const/4 v3, 0x0

    .local v3, "i":I
    :goto_1
    if-ge v3, v0, :cond_3

    .line 728
    iget-object v10, p0, Landroid/support/constraint/solver/widgets/ConstraintWidgetContainer;->mChildren:Ljava/util/ArrayList;

    invoke-virtual {v10, v3}, Ljava/util/ArrayList;->get(I)Ljava/lang/Object;

    move-result-object v8

    check-cast v8, Landroid/support/constraint/solver/widgets/ConstraintWidget;

    .line 729
    .local v8, "widget":Landroid/support/constraint/solver/widgets/ConstraintWidget;
    instance-of v10, v8, Landroid/support/constraint/solver/widgets/WidgetContainer;

    if-eqz v10, :cond_1

    .line 730
    check-cast v8, Landroid/support/constraint/solver/widgets/WidgetContainer;

    .end local v8    # "widget":Landroid/support/constraint/solver/widgets/ConstraintWidget;
    invoke-virtual {v8}, Landroid/support/constraint/solver/widgets/WidgetContainer;->layout()V

    .line 727
    :cond_1
    add-int/lit8 v3, v3, 0x1

    goto :goto_1

    .line 717
    .end local v0    # "count":I
    .end local v3    # "i":I
    :cond_2
    iput v11, p0, Landroid/support/constraint/solver/widgets/ConstraintWidgetContainer;->mX:I

    .line 718
    iput v11, p0, Landroid/support/constraint/solver/widgets/ConstraintWidgetContainer;->mY:I

    goto :goto_0

    .line 736
    .restart local v0    # "count":I
    .restart local v3    # "i":I
    :cond_3
    :try_start_0
    iget-object v10, p0, Landroid/support/constraint/solver/widgets/ConstraintWidgetContainer;->mSystem:Landroid/support/constraint/solver/LinearSystem;

    invoke-virtual {v10}, Landroid/support/constraint/solver/LinearSystem;->reset()V

    .line 746
    iget-object v10, p0, Landroid/support/constraint/solver/widgets/ConstraintWidgetContainer;->mSystem:Landroid/support/constraint/solver/LinearSystem;

    const v11, 0x7fffffff

    invoke-virtual {p0, v10, v11}, Landroid/support/constraint/solver/widgets/ConstraintWidgetContainer;->addChildrenToSolver(Landroid/support/constraint/solver/LinearSystem;I)V

    .line 747
    iget-object v10, p0, Landroid/support/constraint/solver/widgets/ConstraintWidgetContainer;->mSystem:Landroid/support/constraint/solver/LinearSystem;

    invoke-virtual {v10}, Landroid/support/constraint/solver/LinearSystem;->minimize()V
    :try_end_0
    .catch Ljava/lang/Exception; {:try_start_0 .. :try_end_0} :catch_0

    .line 751
    :goto_2
    iget-object v10, p0, Landroid/support/constraint/solver/widgets/ConstraintWidgetContainer;->mSystem:Landroid/support/constraint/solver/LinearSystem;

    invoke-virtual {p0, v10, v12}, Landroid/support/constraint/solver/widgets/ConstraintWidgetContainer;->updateChildrenFromSolver(Landroid/support/constraint/solver/LinearSystem;I)V

    .line 753
    iget-object v10, p0, Landroid/support/constraint/solver/widgets/ConstraintWidgetContainer;->mParent:Landroid/support/constraint/solver/widgets/ConstraintWidget;

    if-eqz v10, :cond_5

    .line 754
    invoke-virtual {p0}, Landroid/support/constraint/solver/widgets/ConstraintWidgetContainer;->getWidth()I

    move-result v9

    .line 755
    .local v9, "width":I
    invoke-virtual {p0}, Landroid/support/constraint/solver/widgets/ConstraintWidgetContainer;->getHeight()I

    move-result v2

    .line 757
    .local v2, "height":I
    iget-object v10, p0, Landroid/support/constraint/solver/widgets/ConstraintWidgetContainer;->mSnapshot:Landroid/support/constraint/solver/widgets/Snapshot;

    invoke-virtual {v10, p0}, Landroid/support/constraint/solver/widgets/Snapshot;->applyTo(Landroid/support/constraint/solver/widgets/ConstraintWidget;)V

    .line 758
    iget v10, p0, Landroid/support/constraint/solver/widgets/ConstraintWidgetContainer;->mPaddingLeft:I

    add-int/2addr v10, v9

    iget v11, p0, Landroid/support/constraint/solver/widgets/ConstraintWidgetContainer;->mPaddingRight:I

    add-int/2addr v10, v11

    invoke-virtual {p0, v10}, Landroid/support/constraint/solver/widgets/ConstraintWidgetContainer;->setWidth(I)V

    .line 759
    iget v10, p0, Landroid/support/constraint/solver/widgets/ConstraintWidgetContainer;->mPaddingTop:I

    add-int/2addr v10, v2

    iget v11, p0, Landroid/support/constraint/solver/widgets/ConstraintWidgetContainer;->mPaddingBottom:I

    add-int/2addr v10, v11

    invoke-virtual {p0, v10}, Landroid/support/constraint/solver/widgets/ConstraintWidgetContainer;->setHeight(I)V

    .line 764
    .end local v2    # "height":I
    .end local v9    # "width":I
    :goto_3
    iget-object v10, p0, Landroid/support/constraint/solver/widgets/ConstraintWidgetContainer;->mSystem:Landroid/support/constraint/solver/LinearSystem;

    invoke-virtual {v10}, Landroid/support/constraint/solver/LinearSystem;->getCache()Landroid/support/constraint/solver/Cache;

    move-result-object v10

    invoke-virtual {p0, v10}, Landroid/support/constraint/solver/widgets/ConstraintWidgetContainer;->resetSolverVariables(Landroid/support/constraint/solver/Cache;)V

    .line 765
    invoke-virtual {p0}, Landroid/support/constraint/solver/widgets/ConstraintWidgetContainer;->getRootConstraintContainer()Landroid/support/constraint/solver/widgets/ConstraintWidgetContainer;

    move-result-object v10

    if-ne p0, v10, :cond_4

    .line 766
    invoke-virtual {p0}, Landroid/support/constraint/solver/widgets/ConstraintWidgetContainer;->updateDrawPosition()V

    .line 768
    :cond_4
    return-void

    .line 748
    :catch_0
    move-exception v1

    .line 749
    .local v1, "e":Ljava/lang/Exception;
    invoke-virtual {v1}, Ljava/lang/Exception;->printStackTrace()V

    goto :goto_2

    .line 761
    .end local v1    # "e":Ljava/lang/Exception;
    :cond_5
    iput v6, p0, Landroid/support/constraint/solver/widgets/ConstraintWidgetContainer;->mX:I

    .line 762
    iput v7, p0, Landroid/support/constraint/solver/widgets/ConstraintWidgetContainer;->mY:I

    goto :goto_3
.end method

.method public layoutFindGroups()I
    .locals 21

    .prologue
    .line 973
    const/16 v19, 0x5

    move/from16 v0, v19

    new-array v4, v0, [Landroid/support/constraint/solver/widgets/ConstraintAnchor$Type;

    const/16 v19, 0x0

    sget-object v20, Landroid/support/constraint/solver/widgets/ConstraintAnchor$Type;->LEFT:Landroid/support/constraint/solver/widgets/ConstraintAnchor$Type;

    aput-object v20, v4, v19

    const/16 v19, 0x1

    sget-object v20, Landroid/support/constraint/solver/widgets/ConstraintAnchor$Type;->RIGHT:Landroid/support/constraint/solver/widgets/ConstraintAnchor$Type;

    aput-object v20, v4, v19

    const/16 v19, 0x2

    sget-object v20, Landroid/support/constraint/solver/widgets/ConstraintAnchor$Type;->TOP:Landroid/support/constraint/solver/widgets/ConstraintAnchor$Type;

    aput-object v20, v4, v19

    const/16 v19, 0x3

    sget-object v20, Landroid/support/constraint/solver/widgets/ConstraintAnchor$Type;->BASELINE:Landroid/support/constraint/solver/widgets/ConstraintAnchor$Type;

    aput-object v20, v4, v19

    const/16 v19, 0x4

    sget-object v20, Landroid/support/constraint/solver/widgets/ConstraintAnchor$Type;->BOTTOM:Landroid/support/constraint/solver/widgets/ConstraintAnchor$Type;

    aput-object v20, v4, v19

    .line 978
    .local v4, "dir":[Landroid/support/constraint/solver/widgets/ConstraintAnchor$Type;
    const/4 v11, 0x1

    .line 979
    .local v11, "label":I
    move-object/from16 v0, p0

    iget-object v0, v0, Landroid/support/constraint/solver/widgets/ConstraintWidgetContainer;->mChildren:Ljava/util/ArrayList;

    move-object/from16 v19, v0

    invoke-virtual/range {v19 .. v19}, Ljava/util/ArrayList;->size()I

    move-result v14

    .line 980
    .local v14, "size":I
    const/4 v10, 0x0

    .local v10, "j":I
    :goto_0
    if-ge v10, v14, :cond_a

    .line 981
    move-object/from16 v0, p0

    iget-object v0, v0, Landroid/support/constraint/solver/widgets/ConstraintWidgetContainer;->mChildren:Ljava/util/ArrayList;

    move-object/from16 v19, v0

    move-object/from16 v0, v19

    invoke-virtual {v0, v10}, Ljava/util/ArrayList;->get(I)Ljava/lang/Object;

    move-result-object v18

    check-cast v18, Landroid/support/constraint/solver/widgets/ConstraintWidget;

    .line 982
    .local v18, "widget":Landroid/support/constraint/solver/widgets/ConstraintWidget;
    const/4 v2, 0x0

    .line 984
    .local v2, "anchor":Landroid/support/constraint/solver/widgets/ConstraintAnchor;
    move-object/from16 v0, v18

    iget-object v2, v0, Landroid/support/constraint/solver/widgets/ConstraintWidget;->mLeft:Landroid/support/constraint/solver/widgets/ConstraintAnchor;

    .line 985
    iget-object v0, v2, Landroid/support/constraint/solver/widgets/ConstraintAnchor;->mTarget:Landroid/support/constraint/solver/widgets/ConstraintAnchor;

    move-object/from16 v19, v0

    if-eqz v19, :cond_5

    .line 986
    invoke-static {v2, v11}, Landroid/support/constraint/solver/widgets/ConstraintWidgetContainer;->setGroup(Landroid/support/constraint/solver/widgets/ConstraintAnchor;I)I

    move-result v19

    move/from16 v0, v19

    if-ne v0, v11, :cond_0

    .line 987
    add-int/lit8 v11, v11, 0x1

    .line 993
    :cond_0
    :goto_1
    move-object/from16 v0, v18

    iget-object v2, v0, Landroid/support/constraint/solver/widgets/ConstraintWidget;->mTop:Landroid/support/constraint/solver/widgets/ConstraintAnchor;

    .line 994
    iget-object v0, v2, Landroid/support/constraint/solver/widgets/ConstraintAnchor;->mTarget:Landroid/support/constraint/solver/widgets/ConstraintAnchor;

    move-object/from16 v19, v0

    if-eqz v19, :cond_6

    .line 995
    invoke-static {v2, v11}, Landroid/support/constraint/solver/widgets/ConstraintWidgetContainer;->setGroup(Landroid/support/constraint/solver/widgets/ConstraintAnchor;I)I

    move-result v19

    move/from16 v0, v19

    if-ne v0, v11, :cond_1

    .line 996
    add-int/lit8 v11, v11, 0x1

    .line 1002
    :cond_1
    :goto_2
    move-object/from16 v0, v18

    iget-object v2, v0, Landroid/support/constraint/solver/widgets/ConstraintWidget;->mRight:Landroid/support/constraint/solver/widgets/ConstraintAnchor;

    .line 1003
    iget-object v0, v2, Landroid/support/constraint/solver/widgets/ConstraintAnchor;->mTarget:Landroid/support/constraint/solver/widgets/ConstraintAnchor;

    move-object/from16 v19, v0

    if-eqz v19, :cond_7

    .line 1004
    invoke-static {v2, v11}, Landroid/support/constraint/solver/widgets/ConstraintWidgetContainer;->setGroup(Landroid/support/constraint/solver/widgets/ConstraintAnchor;I)I

    move-result v19

    move/from16 v0, v19

    if-ne v0, v11, :cond_2

    .line 1005
    add-int/lit8 v11, v11, 0x1

    .line 1011
    :cond_2
    :goto_3
    move-object/from16 v0, v18

    iget-object v2, v0, Landroid/support/constraint/solver/widgets/ConstraintWidget;->mBottom:Landroid/support/constraint/solver/widgets/ConstraintAnchor;

    .line 1012
    iget-object v0, v2, Landroid/support/constraint/solver/widgets/ConstraintAnchor;->mTarget:Landroid/support/constraint/solver/widgets/ConstraintAnchor;

    move-object/from16 v19, v0

    if-eqz v19, :cond_8

    .line 1013
    invoke-static {v2, v11}, Landroid/support/constraint/solver/widgets/ConstraintWidgetContainer;->setGroup(Landroid/support/constraint/solver/widgets/ConstraintAnchor;I)I

    move-result v19

    move/from16 v0, v19

    if-ne v0, v11, :cond_3

    .line 1014
    add-int/lit8 v11, v11, 0x1

    .line 1020
    :cond_3
    :goto_4
    move-object/from16 v0, v18

    iget-object v2, v0, Landroid/support/constraint/solver/widgets/ConstraintWidget;->mBaseline:Landroid/support/constraint/solver/widgets/ConstraintAnchor;

    .line 1021
    iget-object v0, v2, Landroid/support/constraint/solver/widgets/ConstraintAnchor;->mTarget:Landroid/support/constraint/solver/widgets/ConstraintAnchor;

    move-object/from16 v19, v0

    if-eqz v19, :cond_9

    .line 1022
    invoke-static {v2, v11}, Landroid/support/constraint/solver/widgets/ConstraintWidgetContainer;->setGroup(Landroid/support/constraint/solver/widgets/ConstraintAnchor;I)I

    move-result v19

    move/from16 v0, v19

    if-ne v0, v11, :cond_4

    .line 1023
    add-int/lit8 v11, v11, 0x1

    .line 980
    :cond_4
    :goto_5
    add-int/lit8 v10, v10, 0x1

    goto :goto_0

    .line 990
    :cond_5
    const v19, 0x7fffffff

    move/from16 v0, v19

    iput v0, v2, Landroid/support/constraint/solver/widgets/ConstraintAnchor;->mGroup:I

    goto :goto_1

    .line 999
    :cond_6
    const v19, 0x7fffffff

    move/from16 v0, v19

    iput v0, v2, Landroid/support/constraint/solver/widgets/ConstraintAnchor;->mGroup:I

    goto :goto_2

    .line 1008
    :cond_7
    const v19, 0x7fffffff

    move/from16 v0, v19

    iput v0, v2, Landroid/support/constraint/solver/widgets/ConstraintAnchor;->mGroup:I

    goto :goto_3

    .line 1017
    :cond_8
    const v19, 0x7fffffff

    move/from16 v0, v19

    iput v0, v2, Landroid/support/constraint/solver/widgets/ConstraintAnchor;->mGroup:I

    goto :goto_4

    .line 1026
    :cond_9
    const v19, 0x7fffffff

    move/from16 v0, v19

    iput v0, v2, Landroid/support/constraint/solver/widgets/ConstraintAnchor;->mGroup:I

    goto :goto_5

    .line 1029
    .end local v2    # "anchor":Landroid/support/constraint/solver/widgets/ConstraintAnchor;
    .end local v18    # "widget":Landroid/support/constraint/solver/widgets/ConstraintWidget;
    :cond_a
    const/4 v12, 0x1

    .line 1030
    .local v12, "notDone":Z
    const/4 v3, 0x0

    .line 1031
    .local v3, "count":I
    const/4 v5, 0x0

    .line 1035
    .local v5, "fix":I
    :cond_b
    if-eqz v12, :cond_12

    .line 1036
    const/4 v12, 0x0

    .line 1037
    add-int/lit8 v3, v3, 0x1

    .line 1038
    const/4 v10, 0x0

    :goto_6
    if-ge v10, v14, :cond_b

    .line 1039
    move-object/from16 v0, p0

    iget-object v0, v0, Landroid/support/constraint/solver/widgets/ConstraintWidgetContainer;->mChildren:Ljava/util/ArrayList;

    move-object/from16 v19, v0

    move-object/from16 v0, v19

    invoke-virtual {v0, v10}, Ljava/util/ArrayList;->get(I)Ljava/lang/Object;

    move-result-object v18

    check-cast v18, Landroid/support/constraint/solver/widgets/ConstraintWidget;

    .line 1040
    .restart local v18    # "widget":Landroid/support/constraint/solver/widgets/ConstraintWidget;
    const/4 v7, 0x0

    .local v7, "i":I
    :goto_7
    array-length v0, v4

    move/from16 v19, v0

    move/from16 v0, v19

    if-ge v7, v0, :cond_11

    .line 1041
    aget-object v17, v4, v7

    .line 1042
    .local v17, "type":Landroid/support/constraint/solver/widgets/ConstraintAnchor$Type;
    const/4 v2, 0x0

    .line 1043
    .restart local v2    # "anchor":Landroid/support/constraint/solver/widgets/ConstraintAnchor;
    sget-object v19, Landroid/support/constraint/solver/widgets/ConstraintWidgetContainer$2;->$SwitchMap$android$support$constraint$solver$widgets$ConstraintAnchor$Type:[I

    invoke-virtual/range {v17 .. v17}, Landroid/support/constraint/solver/widgets/ConstraintAnchor$Type;->ordinal()I

    move-result v20

    aget v19, v19, v20

    packed-switch v19, :pswitch_data_0

    .line 1065
    :goto_8
    iget-object v0, v2, Landroid/support/constraint/solver/widgets/ConstraintAnchor;->mTarget:Landroid/support/constraint/solver/widgets/ConstraintAnchor;

    move-object/from16 v16, v0

    .line 1066
    .local v16, "target":Landroid/support/constraint/solver/widgets/ConstraintAnchor;
    if-nez v16, :cond_d

    .line 1040
    :cond_c
    :goto_9
    add-int/lit8 v7, v7, 0x1

    goto :goto_7

    .line 1045
    .end local v16    # "target":Landroid/support/constraint/solver/widgets/ConstraintAnchor;
    :pswitch_0
    move-object/from16 v0, v18

    iget-object v2, v0, Landroid/support/constraint/solver/widgets/ConstraintWidget;->mLeft:Landroid/support/constraint/solver/widgets/ConstraintAnchor;

    .line 1047
    goto :goto_8

    .line 1049
    :pswitch_1
    move-object/from16 v0, v18

    iget-object v2, v0, Landroid/support/constraint/solver/widgets/ConstraintWidget;->mTop:Landroid/support/constraint/solver/widgets/ConstraintAnchor;

    .line 1051
    goto :goto_8

    .line 1053
    :pswitch_2
    move-object/from16 v0, v18

    iget-object v2, v0, Landroid/support/constraint/solver/widgets/ConstraintWidget;->mRight:Landroid/support/constraint/solver/widgets/ConstraintAnchor;

    .line 1055
    goto :goto_8

    .line 1057
    :pswitch_3
    move-object/from16 v0, v18

    iget-object v2, v0, Landroid/support/constraint/solver/widgets/ConstraintWidget;->mBottom:Landroid/support/constraint/solver/widgets/ConstraintAnchor;

    .line 1059
    goto :goto_8

    .line 1061
    :pswitch_4
    move-object/from16 v0, v18

    iget-object v2, v0, Landroid/support/constraint/solver/widgets/ConstraintWidget;->mBaseline:Landroid/support/constraint/solver/widgets/ConstraintAnchor;

    goto :goto_8

    .line 1070
    .restart local v16    # "target":Landroid/support/constraint/solver/widgets/ConstraintAnchor;
    :cond_d
    move-object/from16 v0, v16

    iget-object v0, v0, Landroid/support/constraint/solver/widgets/ConstraintAnchor;->mOwner:Landroid/support/constraint/solver/widgets/ConstraintWidget;

    move-object/from16 v19, v0

    invoke-virtual/range {v19 .. v19}, Landroid/support/constraint/solver/widgets/ConstraintWidget;->getParent()Landroid/support/constraint/solver/widgets/ConstraintWidget;

    move-result-object v19

    if-eqz v19, :cond_e

    move-object/from16 v0, v16

    iget v0, v0, Landroid/support/constraint/solver/widgets/ConstraintAnchor;->mGroup:I

    move/from16 v19, v0

    iget v0, v2, Landroid/support/constraint/solver/widgets/ConstraintAnchor;->mGroup:I

    move/from16 v20, v0

    move/from16 v0, v19

    move/from16 v1, v20

    if-eq v0, v1, :cond_e

    .line 1071
    iget v0, v2, Landroid/support/constraint/solver/widgets/ConstraintAnchor;->mGroup:I

    move/from16 v19, v0

    move-object/from16 v0, v16

    iget v0, v0, Landroid/support/constraint/solver/widgets/ConstraintAnchor;->mGroup:I

    move/from16 v20, v0

    move/from16 v0, v19

    move/from16 v1, v20

    if-le v0, v1, :cond_f

    move-object/from16 v0, v16

    iget v0, v0, Landroid/support/constraint/solver/widgets/ConstraintAnchor;->mGroup:I

    move/from16 v19, v0

    :goto_a
    move/from16 v0, v19

    iput v0, v2, Landroid/support/constraint/solver/widgets/ConstraintAnchor;->mGroup:I

    move/from16 v0, v19

    move-object/from16 v1, v16

    iput v0, v1, Landroid/support/constraint/solver/widgets/ConstraintAnchor;->mGroup:I

    .line 1072
    add-int/lit8 v5, v5, 0x1

    .line 1073
    const/4 v12, 0x1

    .line 1076
    :cond_e
    invoke-virtual/range {v16 .. v16}, Landroid/support/constraint/solver/widgets/ConstraintAnchor;->getOpposite()Landroid/support/constraint/solver/widgets/ConstraintAnchor;

    move-result-object v13

    .line 1077
    .local v13, "opposite":Landroid/support/constraint/solver/widgets/ConstraintAnchor;
    if-eqz v13, :cond_c

    iget v0, v13, Landroid/support/constraint/solver/widgets/ConstraintAnchor;->mGroup:I

    move/from16 v19, v0

    iget v0, v2, Landroid/support/constraint/solver/widgets/ConstraintAnchor;->mGroup:I

    move/from16 v20, v0

    move/from16 v0, v19

    move/from16 v1, v20

    if-eq v0, v1, :cond_c

    .line 1078
    iget v0, v2, Landroid/support/constraint/solver/widgets/ConstraintAnchor;->mGroup:I

    move/from16 v19, v0

    iget v0, v13, Landroid/support/constraint/solver/widgets/ConstraintAnchor;->mGroup:I

    move/from16 v20, v0

    move/from16 v0, v19

    move/from16 v1, v20

    if-le v0, v1, :cond_10

    iget v0, v13, Landroid/support/constraint/solver/widgets/ConstraintAnchor;->mGroup:I

    move/from16 v19, v0

    :goto_b
    move/from16 v0, v19

    iput v0, v2, Landroid/support/constraint/solver/widgets/ConstraintAnchor;->mGroup:I

    move/from16 v0, v19

    iput v0, v13, Landroid/support/constraint/solver/widgets/ConstraintAnchor;->mGroup:I

    .line 1079
    add-int/lit8 v5, v5, 0x1

    .line 1080
    const/4 v12, 0x1

    goto/16 :goto_9

    .line 1071
    .end local v13    # "opposite":Landroid/support/constraint/solver/widgets/ConstraintAnchor;
    :cond_f
    iget v0, v2, Landroid/support/constraint/solver/widgets/ConstraintAnchor;->mGroup:I

    move/from16 v19, v0

    goto :goto_a

    .line 1078
    .restart local v13    # "opposite":Landroid/support/constraint/solver/widgets/ConstraintAnchor;
    :cond_10
    iget v0, v2, Landroid/support/constraint/solver/widgets/ConstraintAnchor;->mGroup:I

    move/from16 v19, v0

    goto :goto_b

    .line 1038
    .end local v2    # "anchor":Landroid/support/constraint/solver/widgets/ConstraintAnchor;
    .end local v13    # "opposite":Landroid/support/constraint/solver/widgets/ConstraintAnchor;
    .end local v16    # "target":Landroid/support/constraint/solver/widgets/ConstraintAnchor;
    .end local v17    # "type":Landroid/support/constraint/solver/widgets/ConstraintAnchor$Type;
    :cond_11
    add-int/lit8 v10, v10, 0x1

    goto/16 :goto_6

    .line 1087
    .end local v7    # "i":I
    .end local v18    # "widget":Landroid/support/constraint/solver/widgets/ConstraintWidget;
    :cond_12
    const/4 v8, 0x0

    .line 1088
    .local v8, "index":I
    move-object/from16 v0, p0

    iget-object v0, v0, Landroid/support/constraint/solver/widgets/ConstraintWidgetContainer;->mChildren:Ljava/util/ArrayList;

    move-object/from16 v19, v0

    invoke-virtual/range {v19 .. v19}, Ljava/util/ArrayList;->size()I

    move-result v19

    array-length v0, v4

    move/from16 v20, v0

    mul-int v19, v19, v20

    add-int/lit8 v19, v19, 0x1

    move/from16 v0, v19

    new-array v15, v0, [I

    .line 1089
    .local v15, "table":[I
    const/16 v19, -0x1

    move/from16 v0, v19

    invoke-static {v15, v0}, Ljava/util/Arrays;->fill([II)V

    .line 1090
    const/4 v10, 0x0

    move v9, v8

    .end local v8    # "index":I
    .local v9, "index":I
    :goto_c
    if-ge v10, v14, :cond_1b

    .line 1091
    move-object/from16 v0, p0

    iget-object v0, v0, Landroid/support/constraint/solver/widgets/ConstraintWidgetContainer;->mChildren:Ljava/util/ArrayList;

    move-object/from16 v19, v0

    move-object/from16 v0, v19

    invoke-virtual {v0, v10}, Ljava/util/ArrayList;->get(I)Ljava/lang/Object;

    move-result-object v18

    check-cast v18, Landroid/support/constraint/solver/widgets/ConstraintWidget;

    .line 1092
    .restart local v18    # "widget":Landroid/support/constraint/solver/widgets/ConstraintWidget;
    const/4 v2, 0x0

    .line 1094
    .restart local v2    # "anchor":Landroid/support/constraint/solver/widgets/ConstraintAnchor;
    move-object/from16 v0, v18

    iget-object v2, v0, Landroid/support/constraint/solver/widgets/ConstraintWidget;->mLeft:Landroid/support/constraint/solver/widgets/ConstraintAnchor;

    .line 1095
    iget v0, v2, Landroid/support/constraint/solver/widgets/ConstraintAnchor;->mGroup:I

    move/from16 v19, v0

    const v20, 0x7fffffff

    move/from16 v0, v19

    move/from16 v1, v20

    if-eq v0, v1, :cond_1d

    .line 1096
    iget v6, v2, Landroid/support/constraint/solver/widgets/ConstraintAnchor;->mGroup:I

    .line 1097
    .local v6, "g":I
    aget v19, v15, v6

    const/16 v20, -0x1

    move/from16 v0, v19

    move/from16 v1, v20

    if-ne v0, v1, :cond_1c

    .line 1098
    add-int/lit8 v8, v9, 0x1

    .end local v9    # "index":I
    .restart local v8    # "index":I
    aput v9, v15, v6

    .line 1100
    :goto_d
    aget v19, v15, v6

    move/from16 v0, v19

    iput v0, v2, Landroid/support/constraint/solver/widgets/ConstraintAnchor;->mGroup:I

    .line 1103
    .end local v6    # "g":I
    :goto_e
    move-object/from16 v0, v18

    iget-object v2, v0, Landroid/support/constraint/solver/widgets/ConstraintWidget;->mTop:Landroid/support/constraint/solver/widgets/ConstraintAnchor;

    .line 1104
    iget v0, v2, Landroid/support/constraint/solver/widgets/ConstraintAnchor;->mGroup:I

    move/from16 v19, v0

    const v20, 0x7fffffff

    move/from16 v0, v19

    move/from16 v1, v20

    if-eq v0, v1, :cond_14

    .line 1105
    iget v6, v2, Landroid/support/constraint/solver/widgets/ConstraintAnchor;->mGroup:I

    .line 1106
    .restart local v6    # "g":I
    aget v19, v15, v6

    const/16 v20, -0x1

    move/from16 v0, v19

    move/from16 v1, v20

    if-ne v0, v1, :cond_13

    .line 1107
    add-int/lit8 v9, v8, 0x1

    .end local v8    # "index":I
    .restart local v9    # "index":I
    aput v8, v15, v6

    move v8, v9

    .line 1109
    .end local v9    # "index":I
    .restart local v8    # "index":I
    :cond_13
    aget v19, v15, v6

    move/from16 v0, v19

    iput v0, v2, Landroid/support/constraint/solver/widgets/ConstraintAnchor;->mGroup:I

    .line 1112
    .end local v6    # "g":I
    :cond_14
    move-object/from16 v0, v18

    iget-object v2, v0, Landroid/support/constraint/solver/widgets/ConstraintWidget;->mRight:Landroid/support/constraint/solver/widgets/ConstraintAnchor;

    .line 1113
    iget v0, v2, Landroid/support/constraint/solver/widgets/ConstraintAnchor;->mGroup:I

    move/from16 v19, v0

    const v20, 0x7fffffff

    move/from16 v0, v19

    move/from16 v1, v20

    if-eq v0, v1, :cond_16

    .line 1114
    iget v6, v2, Landroid/support/constraint/solver/widgets/ConstraintAnchor;->mGroup:I

    .line 1115
    .restart local v6    # "g":I
    aget v19, v15, v6

    const/16 v20, -0x1

    move/from16 v0, v19

    move/from16 v1, v20

    if-ne v0, v1, :cond_15

    .line 1116
    add-int/lit8 v9, v8, 0x1

    .end local v8    # "index":I
    .restart local v9    # "index":I
    aput v8, v15, v6

    move v8, v9

    .line 1118
    .end local v9    # "index":I
    .restart local v8    # "index":I
    :cond_15
    aget v19, v15, v6

    move/from16 v0, v19

    iput v0, v2, Landroid/support/constraint/solver/widgets/ConstraintAnchor;->mGroup:I

    .line 1121
    .end local v6    # "g":I
    :cond_16
    move-object/from16 v0, v18

    iget-object v2, v0, Landroid/support/constraint/solver/widgets/ConstraintWidget;->mBottom:Landroid/support/constraint/solver/widgets/ConstraintAnchor;

    .line 1122
    iget v0, v2, Landroid/support/constraint/solver/widgets/ConstraintAnchor;->mGroup:I

    move/from16 v19, v0

    const v20, 0x7fffffff

    move/from16 v0, v19

    move/from16 v1, v20

    if-eq v0, v1, :cond_18

    .line 1123
    iget v6, v2, Landroid/support/constraint/solver/widgets/ConstraintAnchor;->mGroup:I

    .line 1124
    .restart local v6    # "g":I
    aget v19, v15, v6

    const/16 v20, -0x1

    move/from16 v0, v19

    move/from16 v1, v20

    if-ne v0, v1, :cond_17

    .line 1125
    add-int/lit8 v9, v8, 0x1

    .end local v8    # "index":I
    .restart local v9    # "index":I
    aput v8, v15, v6

    move v8, v9

    .line 1127
    .end local v9    # "index":I
    .restart local v8    # "index":I
    :cond_17
    aget v19, v15, v6

    move/from16 v0, v19

    iput v0, v2, Landroid/support/constraint/solver/widgets/ConstraintAnchor;->mGroup:I

    .line 1130
    .end local v6    # "g":I
    :cond_18
    move-object/from16 v0, v18

    iget-object v2, v0, Landroid/support/constraint/solver/widgets/ConstraintWidget;->mBaseline:Landroid/support/constraint/solver/widgets/ConstraintAnchor;

    .line 1131
    iget v0, v2, Landroid/support/constraint/solver/widgets/ConstraintAnchor;->mGroup:I

    move/from16 v19, v0

    const v20, 0x7fffffff

    move/from16 v0, v19

    move/from16 v1, v20

    if-eq v0, v1, :cond_1a

    .line 1132
    iget v6, v2, Landroid/support/constraint/solver/widgets/ConstraintAnchor;->mGroup:I

    .line 1133
    .restart local v6    # "g":I
    aget v19, v15, v6

    const/16 v20, -0x1

    move/from16 v0, v19

    move/from16 v1, v20

    if-ne v0, v1, :cond_19

    .line 1134
    add-int/lit8 v9, v8, 0x1

    .end local v8    # "index":I
    .restart local v9    # "index":I
    aput v8, v15, v6

    move v8, v9

    .line 1136
    .end local v9    # "index":I
    .restart local v8    # "index":I
    :cond_19
    aget v19, v15, v6

    move/from16 v0, v19

    iput v0, v2, Landroid/support/constraint/solver/widgets/ConstraintAnchor;->mGroup:I

    .line 1090
    .end local v6    # "g":I
    :cond_1a
    add-int/lit8 v10, v10, 0x1

    move v9, v8

    .end local v8    # "index":I
    .restart local v9    # "index":I
    goto/16 :goto_c

    .line 1139
    .end local v2    # "anchor":Landroid/support/constraint/solver/widgets/ConstraintAnchor;
    .end local v18    # "widget":Landroid/support/constraint/solver/widgets/ConstraintWidget;
    :cond_1b
    return v9

    .restart local v2    # "anchor":Landroid/support/constraint/solver/widgets/ConstraintAnchor;
    .restart local v6    # "g":I
    .restart local v18    # "widget":Landroid/support/constraint/solver/widgets/ConstraintWidget;
    :cond_1c
    move v8, v9

    .end local v9    # "index":I
    .restart local v8    # "index":I
    goto/16 :goto_d

    .end local v6    # "g":I
    .end local v8    # "index":I
    .restart local v9    # "index":I
    :cond_1d
    move v8, v9

    .end local v9    # "index":I
    .restart local v8    # "index":I
    goto/16 :goto_e

    .line 1043
    :pswitch_data_0
    .packed-switch 0x1
        :pswitch_0
        :pswitch_1
        :pswitch_2
        :pswitch_3
        :pswitch_4
    .end packed-switch
.end method

.method public layoutFindGroupsSimple()I
    .locals 6

    .prologue
    const/4 v5, 0x0

    const/4 v4, 0x1

    .line 801
    iget-object v3, p0, Landroid/support/constraint/solver/widgets/ConstraintWidgetContainer;->mChildren:Ljava/util/ArrayList;

    invoke-virtual {v3}, Ljava/util/ArrayList;->size()I

    move-result v1

    .line 802
    .local v1, "size":I
    const/4 v0, 0x0

    .local v0, "j":I
    :goto_0
    if-ge v0, v1, :cond_0

    .line 803
    iget-object v3, p0, Landroid/support/constraint/solver/widgets/ConstraintWidgetContainer;->mChildren:Ljava/util/ArrayList;

    invoke-virtual {v3, v0}, Ljava/util/ArrayList;->get(I)Ljava/lang/Object;

    move-result-object v2

    check-cast v2, Landroid/support/constraint/solver/widgets/ConstraintWidget;

    .line 804
    .local v2, "widget":Landroid/support/constraint/solver/widgets/ConstraintWidget;
    iget-object v3, v2, Landroid/support/constraint/solver/widgets/ConstraintWidget;->mLeft:Landroid/support/constraint/solver/widgets/ConstraintAnchor;

    iput v5, v3, Landroid/support/constraint/solver/widgets/ConstraintAnchor;->mGroup:I

    .line 805
    iget-object v3, v2, Landroid/support/constraint/solver/widgets/ConstraintWidget;->mRight:Landroid/support/constraint/solver/widgets/ConstraintAnchor;

    iput v5, v3, Landroid/support/constraint/solver/widgets/ConstraintAnchor;->mGroup:I

    .line 806
    iget-object v3, v2, Landroid/support/constraint/solver/widgets/ConstraintWidget;->mTop:Landroid/support/constraint/solver/widgets/ConstraintAnchor;

    iput v4, v3, Landroid/support/constraint/solver/widgets/ConstraintAnchor;->mGroup:I

    .line 807
    iget-object v3, v2, Landroid/support/constraint/solver/widgets/ConstraintWidget;->mBottom:Landroid/support/constraint/solver/widgets/ConstraintAnchor;

    iput v4, v3, Landroid/support/constraint/solver/widgets/ConstraintAnchor;->mGroup:I

    .line 808
    iget-object v3, v2, Landroid/support/constraint/solver/widgets/ConstraintWidget;->mBaseline:Landroid/support/constraint/solver/widgets/ConstraintAnchor;

    iput v4, v3, Landroid/support/constraint/solver/widgets/ConstraintAnchor;->mGroup:I

    .line 802
    add-int/lit8 v0, v0, 0x1

    goto :goto_0

    .line 810
    .end local v2    # "widget":Landroid/support/constraint/solver/widgets/ConstraintWidget;
    :cond_0
    const/4 v3, 0x2

    return v3
.end method

.method public layoutWithGroup(I)V
    .locals 11
    .param p1, "numOfGroups"    # I

    .prologue
    const/4 v10, 0x1

    const/4 v9, 0x0

    .line 1146
    iget v4, p0, Landroid/support/constraint/solver/widgets/ConstraintWidgetContainer;->mX:I

    .line 1147
    .local v4, "prex":I
    iget v5, p0, Landroid/support/constraint/solver/widgets/ConstraintWidgetContainer;->mY:I

    .line 1148
    .local v5, "prey":I
    iget-object v8, p0, Landroid/support/constraint/solver/widgets/ConstraintWidgetContainer;->mParent:Landroid/support/constraint/solver/widgets/ConstraintWidget;

    if-eqz v8, :cond_2

    .line 1149
    iget-object v8, p0, Landroid/support/constraint/solver/widgets/ConstraintWidgetContainer;->mSnapshot:Landroid/support/constraint/solver/widgets/Snapshot;

    if-nez v8, :cond_0

    .line 1150
    new-instance v8, Landroid/support/constraint/solver/widgets/Snapshot;

    invoke-direct {v8, p0}, Landroid/support/constraint/solver/widgets/Snapshot;-><init>(Landroid/support/constraint/solver/widgets/ConstraintWidget;)V

    iput-object v8, p0, Landroid/support/constraint/solver/widgets/ConstraintWidgetContainer;->mSnapshot:Landroid/support/constraint/solver/widgets/Snapshot;

    .line 1152
    :cond_0
    iget-object v8, p0, Landroid/support/constraint/solver/widgets/ConstraintWidgetContainer;->mSnapshot:Landroid/support/constraint/solver/widgets/Snapshot;

    invoke-virtual {v8, p0}, Landroid/support/constraint/solver/widgets/Snapshot;->updateFrom(Landroid/support/constraint/solver/widgets/ConstraintWidget;)V

    .line 1157
    iput v9, p0, Landroid/support/constraint/solver/widgets/ConstraintWidgetContainer;->mX:I

    .line 1158
    iput v9, p0, Landroid/support/constraint/solver/widgets/ConstraintWidgetContainer;->mY:I

    .line 1159
    invoke-virtual {p0}, Landroid/support/constraint/solver/widgets/ConstraintWidgetContainer;->resetAnchors()V

    .line 1160
    iget-object v8, p0, Landroid/support/constraint/solver/widgets/ConstraintWidgetContainer;->mSystem:Landroid/support/constraint/solver/LinearSystem;

    invoke-virtual {v8}, Landroid/support/constraint/solver/LinearSystem;->getCache()Landroid/support/constraint/solver/Cache;

    move-result-object v8

    invoke-virtual {p0, v8}, Landroid/support/constraint/solver/widgets/ConstraintWidgetContainer;->resetSolverVariables(Landroid/support/constraint/solver/Cache;)V

    .line 1167
    :goto_0
    iget-object v8, p0, Landroid/support/constraint/solver/widgets/ConstraintWidgetContainer;->mChildren:Ljava/util/ArrayList;

    invoke-virtual {v8}, Ljava/util/ArrayList;->size()I

    move-result v0

    .line 1168
    .local v0, "count":I
    const/4 v3, 0x0

    .local v3, "i":I
    :goto_1
    if-ge v3, v0, :cond_3

    .line 1169
    iget-object v8, p0, Landroid/support/constraint/solver/widgets/ConstraintWidgetContainer;->mChildren:Ljava/util/ArrayList;

    invoke-virtual {v8, v3}, Ljava/util/ArrayList;->get(I)Ljava/lang/Object;

    move-result-object v6

    check-cast v6, Landroid/support/constraint/solver/widgets/ConstraintWidget;

    .line 1170
    .local v6, "widget":Landroid/support/constraint/solver/widgets/ConstraintWidget;
    instance-of v8, v6, Landroid/support/constraint/solver/widgets/WidgetContainer;

    if-eqz v8, :cond_1

    .line 1171
    check-cast v6, Landroid/support/constraint/solver/widgets/WidgetContainer;

    .end local v6    # "widget":Landroid/support/constraint/solver/widgets/ConstraintWidget;
    invoke-virtual {v6}, Landroid/support/constraint/solver/widgets/WidgetContainer;->layout()V

    .line 1168
    :cond_1
    add-int/lit8 v3, v3, 0x1

    goto :goto_1

    .line 1162
    .end local v0    # "count":I
    .end local v3    # "i":I
    :cond_2
    iput v9, p0, Landroid/support/constraint/solver/widgets/ConstraintWidgetContainer;->mX:I

    .line 1163
    iput v9, p0, Landroid/support/constraint/solver/widgets/ConstraintWidgetContainer;->mY:I

    goto :goto_0

    .line 1175
    .restart local v0    # "count":I
    .restart local v3    # "i":I
    :cond_3
    iget-object v8, p0, Landroid/support/constraint/solver/widgets/ConstraintWidgetContainer;->mLeft:Landroid/support/constraint/solver/widgets/ConstraintAnchor;

    iput v9, v8, Landroid/support/constraint/solver/widgets/ConstraintAnchor;->mGroup:I

    .line 1176
    iget-object v8, p0, Landroid/support/constraint/solver/widgets/ConstraintWidgetContainer;->mRight:Landroid/support/constraint/solver/widgets/ConstraintAnchor;

    iput v9, v8, Landroid/support/constraint/solver/widgets/ConstraintAnchor;->mGroup:I

    .line 1177
    iget-object v8, p0, Landroid/support/constraint/solver/widgets/ConstraintWidgetContainer;->mTop:Landroid/support/constraint/solver/widgets/ConstraintAnchor;

    iput v10, v8, Landroid/support/constraint/solver/widgets/ConstraintAnchor;->mGroup:I

    .line 1178
    iget-object v8, p0, Landroid/support/constraint/solver/widgets/ConstraintWidgetContainer;->mBottom:Landroid/support/constraint/solver/widgets/ConstraintAnchor;

    iput v10, v8, Landroid/support/constraint/solver/widgets/ConstraintAnchor;->mGroup:I

    .line 1179
    iget-object v8, p0, Landroid/support/constraint/solver/widgets/ConstraintWidgetContainer;->mSystem:Landroid/support/constraint/solver/LinearSystem;

    invoke-virtual {v8}, Landroid/support/constraint/solver/LinearSystem;->reset()V

    .line 1213
    const/4 v3, 0x0

    :goto_2
    if-ge v3, p1, :cond_4

    .line 1215
    :try_start_0
    iget-object v8, p0, Landroid/support/constraint/solver/widgets/ConstraintWidgetContainer;->mSystem:Landroid/support/constraint/solver/LinearSystem;

    invoke-virtual {p0, v8, v3}, Landroid/support/constraint/solver/widgets/ConstraintWidgetContainer;->addToSolver(Landroid/support/constraint/solver/LinearSystem;I)V

    .line 1216
    iget-object v8, p0, Landroid/support/constraint/solver/widgets/ConstraintWidgetContainer;->mSystem:Landroid/support/constraint/solver/LinearSystem;

    invoke-virtual {v8}, Landroid/support/constraint/solver/LinearSystem;->minimize()V

    .line 1217
    iget-object v8, p0, Landroid/support/constraint/solver/widgets/ConstraintWidgetContainer;->mSystem:Landroid/support/constraint/solver/LinearSystem;

    invoke-virtual {p0, v8, v3}, Landroid/support/constraint/solver/widgets/ConstraintWidgetContainer;->updateFromSolver(Landroid/support/constraint/solver/LinearSystem;I)V
    :try_end_0
    .catch Ljava/lang/Exception; {:try_start_0 .. :try_end_0} :catch_0

    .line 1221
    :goto_3
    iget-object v8, p0, Landroid/support/constraint/solver/widgets/ConstraintWidgetContainer;->mSystem:Landroid/support/constraint/solver/LinearSystem;

    const/4 v9, -0x2

    invoke-virtual {p0, v8, v9}, Landroid/support/constraint/solver/widgets/ConstraintWidgetContainer;->updateFromSolver(Landroid/support/constraint/solver/LinearSystem;I)V

    .line 1213
    add-int/lit8 v3, v3, 0x1

    goto :goto_2

    .line 1218
    :catch_0
    move-exception v1

    .line 1219
    .local v1, "e":Ljava/lang/Exception;
    invoke-virtual {v1}, Ljava/lang/Exception;->printStackTrace()V

    goto :goto_3

    .line 1225
    .end local v1    # "e":Ljava/lang/Exception;
    :cond_4
    iget-object v8, p0, Landroid/support/constraint/solver/widgets/ConstraintWidgetContainer;->mParent:Landroid/support/constraint/solver/widgets/ConstraintWidget;

    if-eqz v8, :cond_6

    .line 1226
    invoke-virtual {p0}, Landroid/support/constraint/solver/widgets/ConstraintWidgetContainer;->getWidth()I

    move-result v7

    .line 1227
    .local v7, "width":I
    invoke-virtual {p0}, Landroid/support/constraint/solver/widgets/ConstraintWidgetContainer;->getHeight()I

    move-result v2

    .line 1229
    .local v2, "height":I
    iget-object v8, p0, Landroid/support/constraint/solver/widgets/ConstraintWidgetContainer;->mSnapshot:Landroid/support/constraint/solver/widgets/Snapshot;

    invoke-virtual {v8, p0}, Landroid/support/constraint/solver/widgets/Snapshot;->applyTo(Landroid/support/constraint/solver/widgets/ConstraintWidget;)V

    .line 1230
    invoke-virtual {p0, v7}, Landroid/support/constraint/solver/widgets/ConstraintWidgetContainer;->setWidth(I)V

    .line 1231
    invoke-virtual {p0, v2}, Landroid/support/constraint/solver/widgets/ConstraintWidgetContainer;->setHeight(I)V

    .line 1237
    .end local v2    # "height":I
    .end local v7    # "width":I
    :goto_4
    invoke-virtual {p0}, Landroid/support/constraint/solver/widgets/ConstraintWidgetContainer;->getRootConstraintContainer()Landroid/support/constraint/solver/widgets/ConstraintWidgetContainer;

    move-result-object v8

    if-ne p0, v8, :cond_5

    .line 1238
    invoke-virtual {p0}, Landroid/support/constraint/solver/widgets/ConstraintWidgetContainer;->updateDrawPosition()V

    .line 1240
    :cond_5
    return-void

    .line 1233
    :cond_6
    iput v4, p0, Landroid/support/constraint/solver/widgets/ConstraintWidgetContainer;->mX:I

    .line 1234
    iput v5, p0, Landroid/support/constraint/solver/widgets/ConstraintWidgetContainer;->mY:I

    goto :goto_4
.end method

.method public reset()V
    .locals 2

    .prologue
    const/4 v1, 0x0

    .line 101
    iget-object v0, p0, Landroid/support/constraint/solver/widgets/ConstraintWidgetContainer;->mSystem:Landroid/support/constraint/solver/LinearSystem;

    invoke-virtual {v0}, Landroid/support/constraint/solver/LinearSystem;->reset()V

    .line 105
    iput v1, p0, Landroid/support/constraint/solver/widgets/ConstraintWidgetContainer;->mPaddingLeft:I

    .line 106
    iput v1, p0, Landroid/support/constraint/solver/widgets/ConstraintWidgetContainer;->mPaddingRight:I

    .line 107
    iput v1, p0, Landroid/support/constraint/solver/widgets/ConstraintWidgetContainer;->mPaddingTop:I

    .line 108
    iput v1, p0, Landroid/support/constraint/solver/widgets/ConstraintWidgetContainer;->mPaddingBottom:I

    .line 109
    invoke-super {p0}, Landroid/support/constraint/solver/widgets/WidgetContainer;->reset()V

    .line 110
    return-void
.end method

.method public setPadding(IIII)V
    .locals 0
    .param p1, "left"    # I
    .param p2, "top"    # I
    .param p3, "right"    # I
    .param p4, "bottom"    # I

    .prologue
    .line 688
    iput p1, p0, Landroid/support/constraint/solver/widgets/ConstraintWidgetContainer;->mPaddingLeft:I

    .line 689
    iput p2, p0, Landroid/support/constraint/solver/widgets/ConstraintWidgetContainer;->mPaddingTop:I

    .line 690
    iput p3, p0, Landroid/support/constraint/solver/widgets/ConstraintWidgetContainer;->mPaddingRight:I

    .line 691
    iput p4, p0, Landroid/support/constraint/solver/widgets/ConstraintWidgetContainer;->mPaddingBottom:I

    .line 692
    return-void
.end method

.method public updateChildrenFromSolver(Landroid/support/constraint/solver/LinearSystem;I)V
    .locals 4
    .param p1, "system"    # Landroid/support/constraint/solver/LinearSystem;
    .param p2, "group"    # I

    .prologue
    .line 671
    invoke-virtual {p0, p1, p2}, Landroid/support/constraint/solver/widgets/ConstraintWidgetContainer;->updateFromSolver(Landroid/support/constraint/solver/LinearSystem;I)V

    .line 672
    iget-object v3, p0, Landroid/support/constraint/solver/widgets/ConstraintWidgetContainer;->mChildren:Ljava/util/ArrayList;

    invoke-virtual {v3}, Ljava/util/ArrayList;->size()I

    move-result v0

    .line 673
    .local v0, "count":I
    const/4 v1, 0x0

    .local v1, "i":I
    :goto_0
    if-ge v1, v0, :cond_0

    .line 674
    iget-object v3, p0, Landroid/support/constraint/solver/widgets/ConstraintWidgetContainer;->mChildren:Ljava/util/ArrayList;

    invoke-virtual {v3, v1}, Ljava/util/ArrayList;->get(I)Ljava/lang/Object;

    move-result-object v2

    check-cast v2, Landroid/support/constraint/solver/widgets/ConstraintWidget;

    .line 675
    .local v2, "widget":Landroid/support/constraint/solver/widgets/ConstraintWidget;
    invoke-virtual {v2, p1, p2}, Landroid/support/constraint/solver/widgets/ConstraintWidget;->updateFromSolver(Landroid/support/constraint/solver/LinearSystem;I)V

    .line 673
    add-int/lit8 v1, v1, 0x1

    goto :goto_0

    .line 677
    .end local v2    # "widget":Landroid/support/constraint/solver/widgets/ConstraintWidget;
    :cond_0
    return-void
.end method
