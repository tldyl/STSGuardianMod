package guardian.cards;


import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.DexterityPower;
import com.megacrit.cardcrawl.powers.LoseDexterityPower;
import com.megacrit.cardcrawl.powers.LoseStrengthPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import guardian.GuardianMod;
import guardian.patches.AbstractCardEnum;

import static guardian.GuardianMod.socketTypes.GREEN;
import static guardian.GuardianMod.socketTypes.RED;


public class Gem_Green extends AbstractGuardianCard {
    public static final String ID = GuardianMod.makeID("Gem_Green");
    public static final String NAME;
    public static String DESCRIPTION;
    public static String UPGRADED_DESCRIPTION;
    public static final String IMG_PATH = "cards/gemGreen.png";
    private static final CardType TYPE = CardType.SKILL;
    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.SELF;

    private static final CardStrings cardStrings;

    //TUNING CONSTANTS

    private static final int COST = 0;
    private static final int DAMAGEBUFF = 1;  //HARDCODED IN DESCRIPTION
    private static final int SOCKETS = 0;
    private static final boolean SOCKETSAREAFTER = true;

    //END TUNING CONSTANTS

    //GEMS ALWAYS need entries added to OnSave, OnLoad, updateGemDescription, render methods in AbstractGuardianCard!

    public Gem_Green() {
        super(ID, NAME, GuardianMod.getResourcePath(IMG_PATH), COST, DESCRIPTION, TYPE, AbstractCardEnum.GUARDIAN, RARITY, TARGET);

        this.baseMagicNumber = this.magicNumber = DAMAGEBUFF;
        this.tags.add(GuardianMod.GEM);
        this.thisGemsType = GREEN;
        this.socketCount = SOCKETS;

    }

    public void use(AbstractPlayer p, AbstractMonster m)
    {
        super.use(p,m);
        gemEffect(p,m);
    }

    public static void gemEffect(AbstractPlayer p, AbstractMonster m){
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new DexterityPower(p, DAMAGEBUFF), DAMAGEBUFF));
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new LoseDexterityPower(p, DAMAGEBUFF), DAMAGEBUFF));

    }

    public AbstractCard makeCopy() {
        return new Gem_Green();
    }

    public void upgrade() {

    }

    public void updateDescription() {
        if (this.socketCount > 0) this.rawDescription = this.updateGemDescription(cardStrings.DESCRIPTION, SOCKETSAREAFTER);
        //GuardianMod.logger.info(DESCRIPTION);
        this.initializeDescription();
    }

    public boolean canUpgrade() {
        return false;
    }

    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
        NAME = cardStrings.NAME;
        DESCRIPTION = cardStrings.DESCRIPTION;
        UPGRADED_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    }
}


