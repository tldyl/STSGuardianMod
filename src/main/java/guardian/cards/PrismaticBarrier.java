package guardian.cards;



import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import guardian.GuardianMod;
import guardian.actions.SwitchToDefenseModeAction;
import guardian.patches.AbstractCardEnum;

public class PrismaticBarrier extends AbstractGuardianCard {
    public static final String ID = GuardianMod.makeID("PrismaticBarrier");
    public static final String NAME;
    public static final String DESCRIPTION;
    public static String UPGRADED_DESCRIPTION;
    public static final String IMG_PATH = "cards/prismaticBarrier.png";

    private static final CardStrings cardStrings;
    private static final CardType TYPE = CardType.SKILL;
    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;

    //TUNING CONSTANTS

    private static final int COST = 1;
    private static final int BLOCK = 3;
    private static final int UPGRADE_BONUS = 0;
    private static final int MULTICOUNT = 1;
    private static final int SOCKETS = 2;
    private static final boolean SOCKETSAREAFTER = true;

    //END TUNING CONSTANTS

    public PrismaticBarrier() {
        super(ID, NAME, GuardianMod.getResourcePath(IMG_PATH), COST, DESCRIPTION, TYPE, AbstractCardEnum.GUARDIAN, RARITY, TARGET);


        this.baseBlock = BLOCK;
        this.socketCount = SOCKETS;
        this.updateDescription();

        this.multihit = MULTICOUNT;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        super.use(p,m);
        for (int i = 0; i < this.multihit; i++) {
            AbstractDungeon.actionManager.addToBottom(new GainBlockAction(p, p, this.block));
        }
        this.useGems(p, m);

    }

    public AbstractCard makeCopy() {
        return new PrismaticBarrier();
    }

    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();

            this.socketCount++;
            this.updateDescription();
        }
    }

    @Override
    public float calculateModifiedCardDamage(AbstractPlayer player, AbstractMonster mo, float tmp) {
        super.calculateModifiedCardDamage(player, mo, tmp);
        this.multihit = 1 + this.sockets.size();
        return tmp;

    }

    public void updateDescription() {
        if (this.socketCount > 0) this.rawDescription = this.updateGemDescription(cardStrings.DESCRIPTION, SOCKETSAREAFTER);
        //GuardianMod.logger.info(DESCRIPTION);
        this.initializeDescription();
    }

    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
        NAME = cardStrings.NAME;
        DESCRIPTION = cardStrings.DESCRIPTION;
        UPGRADED_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    }
}


