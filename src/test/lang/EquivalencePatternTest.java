package lang;


import lang.java8.pat.ast.MatchEqv;
import lang.ast.FormalPredicate;
import lang.ast.FormalPredicateMap;
import lang.ast.Program;

import static org.junit.jupiter.api.Assertions.*;
import org.apache.commons.collections4.SetUtils;

import java.util.Collections;
import java.util.Set;

import org.junit.jupiter.api.Test;
import lang.java8.pat.ast.ASTNode;
import lang.cons.ObjLangASTNode;
import java.util.Map;
import java.util.HashMap;

public class EquivalencePatternTest {
    @Test public void SimpleTest() {


        java.util.List<ObjLangASTNode> list1 = lang.ast.ASTNode.patternParse("`term1 * `term2 ","java8");
        java.util.List<ObjLangASTNode> list2 = lang.ast.ASTNode.patternParse("3 * 2","java8");
        
        
        //is list length of one?
        assertEquals(1, list1.size());
        assertEquals(1, list2.size());

        ASTNode<ASTNode> smallRoot = (ASTNode<ASTNode>) list1.get(0);
        ASTNode<ASTNode> bigRoot = (ASTNode<ASTNode>) list2.get(0);

       
        
        System.out.println(list1);
        System.out.println(list2);
        //iterate the list
        Map<ASTNode<ASTNode>,  ASTNode<ASTNode>> matchMap = MatchEqv.match(smallRoot,bigRoot);


        //we need to build the actualMap
        Map<ASTNode<ASTNode>,  ASTNode<ASTNode>> actualMap = helpMatch(smallRoot, bigRoot, new HashMap<ASTNode<ASTNode>,  ASTNode<ASTNode>>());

        
        assertEquals(matchMap, actualMap);

        System.out.println(matchMap);
        System.out.println(actualMap);

        //Make sure that the key maps to the right thing

        //is the map mapping to the nodes we want it to?

        //add helper function to refer to them 

        //cli ./gradlew test  --tests "*DomainSeparation*"

        
    }

    @Test public void noMatchTest() {
        java.util.List<ObjLangASTNode> list1 = lang.ast.ASTNode.patternParse("`term1 * `term2 ","java8");
        java.util.List<ObjLangASTNode> list2 = lang.ast.ASTNode.patternParse("3 + 2","java8");
        
        
        //is list length of one?
        assertEquals(1, list1.size());
        assertEquals(1, list2.size());

        ASTNode<ASTNode> smallRoot = (ASTNode<ASTNode>) list1.get(0);
        ASTNode<ASTNode> bigRoot = (ASTNode<ASTNode>) list2.get(0);
        assertEquals(MatchEqv.match(smallRoot, bigRoot),Collections.emptyMap());
    }

    //helper method to create map between smallRoot and bigRoots
    private Map<ASTNode<ASTNode>,ASTNode<ASTNode>> helpMatch(ASTNode<ASTNode> smallRoot,ASTNode<ASTNode> bigRoot,Map<ASTNode<ASTNode>,ASTNode<ASTNode>> helperMap) {
        int numChildren = bigRoot.getNumChild();
        if(smallRoot.isMetaVar()) {
            helperMap.put(smallRoot, bigRoot);
            return helperMap;
        }

        for (int i = 0; i < numChildren; i++ ) {
            helpMatch(smallRoot.getChild(i), bigRoot.getChild(i), helperMap);
        }

        helperMap.put(smallRoot, bigRoot);
        return helperMap;
    }

}
