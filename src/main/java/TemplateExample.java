package template;

import com.evacipated.cardcrawl.modthespire.lib.*;
import com.evacipated.cardcrawl.modthespire.patcher.PatchingException;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.monsters.EnemyMoveInfo;
import javassist.CannotCompileException;
import javassist.CtBehavior;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@SpirePatch(
        clz = AbstractMonster.class,
        method = "damage",
        paramtypez = {DamageInfo.class}
)
public class TemplateExample {
    private static final Logger logger = LogManager.getLogger(TemplateExample.class.getName());

    @SpireInsertPatch(
            locator = TemplateExample.Locator.class,
            localvars = {"damageAmount", "intentDmg", "move"}
    )
    public static void InsertPre(AbstractMonster __instance, DamageInfo info, @ByRef int[] damageAmount, @ByRef int[] intentDmg, EnemyMoveInfo move) {
        logger.debug("Template");
    }

    private static class Locator extends SpireInsertLocator {
        public int[] Locate(CtBehavior ctMethodToPatch) throws CannotCompileException, PatchingException {
            Matcher finalMatcher = new Matcher.MethodCallMatcher(AbstractMonster.class, "decrementBlock");
            return LineFinder.findInOrder(ctMethodToPatch, finalMatcher);
        }
    }
}
