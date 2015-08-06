/*
 * =============================================================================
 *
 *   Copyright (c) 2011-2014, The THYMELEAF team (http://www.thymeleaf.org)
 *
 *   Licensed under the Apache License, Version 2.0 (the "License");
 *   you may not use this file except in compliance with the License.
 *   You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *   Unless required by applicable law or agreed to in writing, software
 *   distributed under the License is distributed on an "AS IS" BASIS,
 *   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *   See the License for the specific language governing permissions and
 *   limitations under the License.
 *
 * =============================================================================
 */
package org.thymeleaf.templateparser.text;

import java.io.CharArrayReader;
import java.util.List;

import junit.framework.ComparisonFailure;
import junit.framework.TestCase;


/*
 *
 * @author Daniel Fernandez
 * @since 2.0.0
 */
public class TextParserTest extends TestCase {

    private static int totalTestExecutions = 0;
    

    public void test() throws Exception {


//        testDoc(
//                "/*hello*/",
//                "[C(hello){1,1}]",
//                "[T(hello){1,1}]");
        testDoc(
                "",
                "[]");
        testDoc(
                "<div class= s>",
                "[T(<div class= s>){1,1}]");
        testDoc(
                "<html>",
                "[T(<html>){1,1}]");
        testDoc(
                "<html></html>",
                "[T(<html></html>){1,1}]");
        testDoc(
                "<html><body></html>",
                "[T(<html><body></html>){1,1}]");
        testDoc(
                "<html>",
                "[T(<html>){1,1}]");
        testDoc(
                "<html></html>",
                "[T(<html></html>){1,1}]");
        testDoc(
                "<html><body></html>",
                "[T(<html><body></html>){1,1}]");
        testDoc(
                "<html><title><body><p>",
                "[T(<html><title><body><p>){1,1}]");
        testDoc(
                "[[title]][[body][[p]]",
                "[T([[title]][[body][[p]]){1,1}]");
        testDoc(
                "[title][body][p]",
                "[T([title][body][p]){1,1}]");
        testDoc(
                "[[#hello]]...[[/hello]]",
                "[T([){1,1}OES(hello){1,2}OEE(hello){1,9}T(]...[){1,10}CES(hello){1,15}CEE(hello){1,22}T(]){1,23}]");
        testDoc(
                "[#hello]...[/hello]",
                "[OES(hello){1,1}OEE(hello){1,9}T(...){1,11}CES(hello){1,14}CEE(hello){1,22}]");
        testDoc(
                "...[#hello]...[/hello]...",
                "[T(...){1,1}OES(hello){1,4}OEE(hello){1,12}T(...){1,14}CES(hello){1,17}CEE(hello){1,25}T(...){1,27}]");
        testDoc(
                "...[#hello]...[#bye/][/hello]...",
                "[T(...){1,1}OES(hello){1,4}OEE(hello){1,12}T(...){1,14}SES(bye){1,17}SEE(bye){1,23}CES(hello){1,26}CEE(hello){1,34}T(...){1,36}]");
        testDoc(
                "...[#hello src=\"hello\"]...[#bye/][/hello]...",
                "[T(...){1,1}OES(hello){1,4}A(src){1,13}(=){1,16}(\"hello\"){1,17}OEE(hello){1,24}T(...){1,26}SES(bye){1,29}SEE(bye){1,35}CES(hello){1,38}CEE(hello){1,46}T(...){1,48}]");
        testDoc(
                "...[#hello src=\"hello\"]...[#bye alt=\"hello\"/][/hello]...",
                "[T(...){1,1}OES(hello){1,4}A(src){1,13}(=){1,16}(\"hello\"){1,17}OEE(hello){1,24}T(...){1,26}SES(bye){1,29}A(alt){1,36}(=){1,39}(\"hello\"){1,40}SEE(bye){1,47}CES(hello){1,50}CEE(hello){1,58}T(...){1,60}]");
        testDoc(
                "...[#hello   src=\"hello\"  ]...[#bye alt=\"hello\"  /][/hello]...",
                "[T(...){1,1}OES(hello){1,4}A(src){1,15}(=){1,18}(\"hello\"){1,19}OEE(hello){1,28}T(...){1,30}SES(bye){1,33}A(alt){1,40}(=){1,43}(\"hello\"){1,44}SEE(bye){1,53}CES(hello){1,56}CEE(hello){1,64}T(...){1,66}]");
        testDoc(
                "...[#hello \nsrc=\"hello\" ]...[#bye   /][/hello]...",
                "[T(...){1,1}OES(hello){1,4}A(src){2,1}(=){2,4}(\"hello\"){2,5}OEE(hello){2,13}T(...){2,15}SES(bye){2,18}SEE(bye){2,27}CES(hello){2,30}CEE(hello){2,38}T(...){2,40}]");
        testDoc(
                "...[#hello \nsrc=\"hello\" bee ]...[#bye alt  /][/hello]...",
                "[T(...){1,1}OES(hello){1,4}A(src){2,1}(=){2,4}(\"hello\"){2,5}A(bee){2,13}(){2,16}(){2,16}OEE(hello){2,17}T(...){2,19}SES(bye){2,22}A(alt){2,29}(){2,32}(){2,32}SEE(bye){2,34}CES(hello){2,37}CEE(hello){2,45}T(...){2,47}]");
        testDoc(
                "[#hello][/hello]",
                "[OES(hello){1,1}OEE(hello){1,9}CES(hello){1,11}CEE(hello){1,19}]");
        testDoc(
                "[#hello/]",
                "[SES(hello){1,1}SEE(hello){1,9}]");
        testDoc(
                "[#][/]",
                "[OES(){1,1}OEE(){1,4}CES(){1,6}CEE(){1,9}]");
        testDoc(
                "[#/]",
                "[SES(){1,1}SEE(){1,4}]");
        testDoc(
                "...[#   src=\"hello\"  ]...[# alt=\"hello\"  /][/]...",
                "[T(...){1,1}OES(){1,4}A(src){1,10}(=){1,13}(\"hello\"){1,14}OEE(){1,23}T(...){1,25}SES(){1,28}A(alt){1,32}(=){1,35}(\"hello\"){1,36}SEE(){1,45}CES(){1,48}CEE(){1,51}T(...){1,53}]");
        testDoc(
                "...[#   src='hello'  ]...[# alt='hello'  /][/]...",
                "[T(...){1,1}OES(){1,4}A(src){1,10}(=){1,13}('hello'){1,14}OEE(){1,23}T(...){1,25}SES(){1,28}A(alt){1,32}(=){1,35}('hello'){1,36}SEE(){1,45}CES(){1,48}CEE(){1,51}T(...){1,53}]");
        testDoc(
                "...[#   src=hello  ]...[# alt=hello  /][/]...",
                "[T(...){1,1}OES(){1,4}A(src){1,10}(=){1,13}(hello){1,14}OEE(){1,21}T(...){1,23}SES(){1,26}A(alt){1,30}(=){1,33}(hello){1,34}SEE(){1,41}CES(){1,44}CEE(){1,47}T(...){1,49}]");
        testDocError(
                "...[#   src=\"hello\"  ]...[# alt=\"hello\"  /]...",
                null,
                -1, -1);
        testDoc(
                "[#{hello}/]",
                "[T([#{hello}/]){1,1}]");
        testDoc(
                "[#template] if (a < 0) { do this} [/template]",
                "[OES(template){1,1}OEE(template){1,12}T( if (a < 0) { do this} ){1,14}CES(template){1,37}CEE(template){1,48}]");
        testDoc(
                "[#template a='zero' b='one' /]",
                "[SES(template){1,1}A(a){1,13}(=){1,14}('zero'){1,15}A(b){1,22}(=){1,23}('one'){1,24}SEE(template){1,30}]");
        testDoc(
                "[#template a='zero' b='one']\n\naaaaa\n\n[/template]",
                "[OES(template){1,1}A(a){1,13}(=){1,14}('zero'){1,15}A(b){1,22}(=){1,23}('one'){1,24}OEE(template){1,29}T(\n\naaaaa\n\n){1,31}CES(template){5,1}CEE(template){5,12}]");
        testDoc(
                "Hello, World!",
                "[T(Hello, World!){1,1}]");
        testDoc(
                "Hello, World!",
                "[T(ello, Worl){1,1}]",
                1, 10);
        testDoc(
                "[#img src=\"hello\"/]Something",
                "[SES(img){1,1}A(src){1,8}(=){1,11}(\"hello\"){1,12}SEE(img){1,19}T(Something){1,22}]");
        testDoc(
                "[#li a=\"a [# 0]\"]Hello[/li]",
                "[OES(li){1,1}A(a){1,7}(=){1,8}(\"a [# 0]\"){1,9}OEE(li){1,20}T(Hello){1,22}CES(li){1,27}CEE(li){1,32}]");
        testDoc(
                "Hello, [#p]lal'a[/p]",
                "[T(Hello, ){1,1}OES(p){1,8}OEE(p){1,12}T(lal'a){1,14}CES(p){1,19}CEE(p){1,23}]");
        testDoc(
                "Hello, [#p]l'al'a[/p]",
                "[T(Hello, ){1,1}OES(p){1,8}OEE(p){1,12}T(l'al'a){1,14}CES(p){1,20}CEE(p){1,24}]");
        testDoc(
                "Hello, [#br th:text =   'll'a=2/]",
                "[T(Hello, ){1,1}SES(br){1,8}A(th:text){1,14}( =   ){1,21}('ll'){1,26}A(a){1,30}(=){1,31}(2){1,32}SEE(br){1,33}]");
        testDoc(
                "Hello, [#br th:text = a=b/]",
                "[T(Hello, ){1,1}SES(br){1,8}A(th:text){1,14}( = ){1,21}(a=b){1,24}SEE(br){1,27}]");
        testDoc(
                "Hello, World! [#br/]\n[#div\n l\n     a=\"12 3\" zas    o=\"\"  b=\"lelo\n  = s\"]lala[/div] [#p th=\"lala\" ]liool[/p]",
                "[T(Hello, World! ){1,1}SES(br){1,15}SEE(br){1,20}T(\n){1,23}" +
                        "OES(div){2,1}A(l){3,2}(){3,3}(){3,3}A(a){4,6}(=){4,7}(\"12 3\"){4,8}A(zas){4,15}(){4,18}(){4,18}A(o){4,22}(=){4,23}(\"\"){4,24}A(b){4,28}(=){4,29}(\"lelo\n  = s\"){4,30}" +
                        "OEE(div){5,7}T(lala){5,9}CES(div){5,13}CEE(div){5,19}T( ){5,21}OES(p){5,22}A(th){5,27}(=){5,29}(\"lala\"){5,30}OEE(p){5,37}T(liool){5,39}CES(p){5,44}CEE(p){5,48}]");
        testDoc(
                "kl\njasdl kjaslkj asjqq9\nk fiuh 23kj hdfkjh assd\nflkjh lkjh fdfasdfkjlh dfs" +
                        "llk\nd8u u hkkj asyu 4lk vl jhksajhd889p3rk sl a, alkj a9\n))sad lkjsalkja aslk" +
                        "la \n&aacute; lasd &amp; aiass da & asdll . asi ua&$\" khj askjh 1 kh ak hhjh" +
                        "kljasdl kjaslkj asjqq9k fiuh 23kj hdfkjh assdflkjh lkjh fdfa\nsdfkjlh dfs" +
                        "llkd8u u \nhkkj asyu 4lk vl jhksajhd889p3rk sl a, alkj a9))sad l\nkjsalkja aslk" +
                        "la &aacute;\n lasd &amp; aiass da & asdll . asi ua&$\" khj askjh 1 kh ak hh\njh",
                "[T(kl\njasdl kjaslkj asjqq9\nk fiuh 23kj hdfkjh assd\nflkjh lkjh fdfasdfkjlh dfs" +
                        "llk\nd8u u hkkj asyu 4lk vl jhksajhd889p3rk sl a, alkj a9\n))sad lkjsalkja aslk" +
                        "la \n&aacute; lasd &amp; aiass da & asdll . asi ua&$\" khj askjh 1 kh ak hhjh" +
                        "kljasdl kjaslkj asjqq9k fiuh 23kj hdfkjh assdflkjh lkjh fdfa\nsdfkjlh dfs" +
                        "llkd8u u \nhkkj asyu 4lk vl jhksajhd889p3rk sl a, alkj a9))sad l\nkjsalkja aslk" +
                        "la &aacute;\n lasd &amp; aiass da & asdll . asi ua&$\" khj askjh 1 kh ak hh\njh){1,1}]");
        testDoc(
                "kl\njasdl kjaslkj asjqq9\nk fiuh 23kj hdfkjh assd\nflkjh lkjh fdfasdfkjlh dfs" +
                        "llk\nd8u u hkkj asyu 4lk vl jhksajhd889p3rk sl a, alkj a9\n))sad lkjsalkja aslk" +
                        "la \n&aacute; lasd &amp; aiass da & asdll . asi ua&$\" khj askjh 1 kh ak hhjh" +
                        "kljasdl kjaslkj asjqq9k fiuh 23kj hdfkjh assdflkjh lkjh fdfa\nsdfkjlh dfs" +
                        "llkd8u u \nhkkj asyu 4lk vl jhksajhd889p3rk sl a, alkj a9))sad l\nkjsalkja aslk" +
                        "la &aacute;\n lasd &amp; [#p] aiass da & asdll . asi ua&$\" khj askjh 1 kh ak hh\njh" +
                        "kl\njasdl kjaslkj asjqq9\nk fiuh 23kj hdfkjh assd\nflkjh lkjh fdfasdfkjlh dfs" +
                        "llk\nd8u u hkkj asyu 4lk vl jhksajhd889p3rk sl a, alkj a9\n))sad lkjsalkja aslk" +
                        "la \n&aacute; lasd &amp; aiass da & asdll . asi ua&$\" khj askjh 1 kh ak hhjh" +
                        "kljasdl kjaslkj asjqq9k fiuh 23kj hdfkjh assdflkjh lkjh fdfa\nsdfkjlh dfs" +
                        "llkd8u u \nhkkj asyu 4lk vl jhksajhd889p3rk sl a, alkj a9))sad l\nkjsalkja aslk" +
                        "la &aacute;\n lasd &amp; aiass da & asdll . asi ua&$\" [/p] khj askjh 1 kh ak hh\njh" +
                        "kl\njasdl kjaslkj asjqq9\nk fiuh 23kj hdfkjh assd\nflkjh lkjh fdfasdfkjlh dfs" +
                        "llk\nd8u u hkkj asyu 4lk vl jhksajhd889p3rk sl a, alkj a9\n))sad lkjsalkja aslk" +
                        "la \n&aacute; lasd &amp; aiass da & asdll . asi ua&$\" khj askjh 1 kh ak hhjh" +
                        "kljasdl kjaslkj asjqq9k fiuh 23kj hdfkjh assdflkjh lkjh fdfa\nsdfkjlh dfs" +
                        "llkd8u u \nhkkj asyu 4lk vl jhksajhd889p3rk sl a, alkj a9))sad l\nkjsalkja aslk" +
                        "la &aacute;\n lasd &amp; aiass da & asdll . asi ua&$\" khj askjh 1 kh ak hh\njh" +
                        "kl\njasdl kjaslkj asjqq9\nk fiuh 23kj hdfkjh assd\nflkjh lkjh fdfasdfkjlh dfs" +
                        "llk\nd8u u hkkj asyu 4lk vl jhksajhd889p3rk sl a, alkj a9\n))sad lkjsalkja aslk" +
                        "la \n&aacute; lasd &amp; aiass da & asdll . asi ua&$\" khj askjh 1 kh ak hhjh" +
                        "kljasdl kjaslkj asjqq9k fiuh 23kj hdfkjh assdflkjh lkjh fdfa\nsdfkjlh dfs" +
                        "llkd8u u \nhkkj asyu 4lk vl jhksajhd889p3rk sl a, alkj a9))sad l\nkjsalkja aslk" +
                        "la &aacute;\n lasd &amp; aiass da & asdll . asi ua&$\" khj askjh 1 kh ak hh\njh" +
                        "kl\njasdl kjaslkj asjqq9\nk fiuh 23kj hdfkjh assd\nflkjh lkjh fdfasdfkjlh dfs" +
                        "llk\nd8u u hkkj asyu 4lk vl jhksajhd889p3rk sl a, alkj a9\n))sad lkjsalkja aslk" +
                        "la \n&aacute; lasd &amp; aiass da & asdll . asi ua&$\" khj askjh 1 kh ak hhjh" +
                        "kljasdl kjaslkj asjqq9k fiuh 23kj hdfkjh assdflkjh lkjh fdfa\nsdfkjlh dfs" +
                        "llkd8u u \nhkkj asyu 4lk vl jhksajhd889p3rk sl a, alkj a9))sad l\nkjsalkja aslk" +
                        "la &aacute;\n lasd &amp; aiass da & asdll . asi ua&$\" khj askjh 1 kh ak hh\njh" +
                        "kl\njasdl kjaslkj asjqq9\nk fiuh 23kj hdfkjh assd\nflkjh lkjh fdfasdfkjlh dfs" +
                        "llk\nd8u u hkkj asyu 4lk vl jhksajhd889p3rk sl a, alkj a9\n))sad lkjsalkja aslk" +
                        "la \n&aacute; lasd &amp; aiass da & asdll . asi ua&$\" khj askjh 1 kh ak hhjh" +
                        "kljasdl kjaslkj asjqq9k fiuh 23kj hdfkjh assdflkjh lkjh fdfa\nsdfkjlh dfs" +
                        "llkd8u u \nhkkj asyu 4lk vl jhksajhd889p3rk sl a, alkj a9))sad l\nkjsalkja aslk" +
                        "la &aacute;\n lasd &amp; aiass da & asdll . asi ua&$\" khj askjh 1 kh ak hh\njh" +
                        "kl\njasdl kjaslkj asjqq9\nk fiuh 23kj hdfkjh assd\nflkjh lkjh fdfasdfkjlh dfs" +
                        "llk\nd8u u hkkj asyu 4lk vl jhksajhd889p3rk sl a, alkj a9\n))sad lkjsalkja aslk" +
                        "la \n&aacute; lasd &amp; aiass da & asdll . asi ua&$\" khj askjh 1 kh ak hhjh" +
                        "kljasdl kjaslkj asjqq9k fiuh 23kj hdfkjh assdflkjh lkjh fdfa\nsdfkjlh dfs" +
                        "llkd8u u \nhkkj asyu 4lk vl jhksajhd889p3rk sl a, alkj a9))sad l\nkjsalkja aslk" +
                        "la &aacute;\n lasd &amp; aiass da & asdll . asi ua&$\" khj askjh 1 kh ak hh\njh",
                "[T(kl\n" +
                        "jasdl kjaslkj asjqq9\n" +
                        "k fiuh 23kj hdfkjh assd\n" +
                        "flkjh lkjh fdfasdfkjlh dfsllk\n" +
                        "d8u u hkkj asyu 4lk vl jhksajhd889p3rk sl a, alkj a9\n" +
                        "))sad lkjsalkja aslkla \n" +
                        "&aacute; lasd &amp; aiass da & asdll . asi ua&$\" khj askjh 1 kh ak hhjhkljasdl kjaslkj asjqq9k fiuh 23kj hdfkjh assdflkjh lkjh fdfa\n" +
                        "sdfkjlh dfsllkd8u u \n" +
                        "hkkj asyu 4lk vl jhksajhd889p3rk sl a, alkj a9))sad l\n" +
                        "kjsalkja aslkla &aacute;\n" +
                        " lasd &amp; ){1,1}OES(p){11,13}OEE(p){11,17}T( aiass da & asdll . asi ua&$\" khj askjh 1 kh ak hh\n" +
                        "jhkl\n" +
                        "jasdl kjaslkj asjqq9\n" +
                        "k fiuh 23kj hdfkjh assd\n" +
                        "flkjh lkjh fdfasdfkjlh dfsllk\n" +
                        "d8u u hkkj asyu 4lk vl jhksajhd889p3rk sl a, alkj a9\n" +
                        "))sad lkjsalkja aslkla \n" +
                        "&aacute; lasd &amp; aiass da & asdll . asi ua&$\" khj askjh 1 kh ak hhjhkljasdl kjaslkj asjqq9k fiuh 23kj hdfkjh assdflkjh lkjh fdfa\n" +
                        "sdfkjlh dfsllkd8u u \n" +
                        "hkkj asyu 4lk vl jhksajhd889p3rk sl a, alkj a9))sad l\n" +
                        "kjsalkja aslkla &aacute;\n" +
                        " lasd &amp; aiass da & asdll . asi ua&$\" ){11,19}CES(p){22,42}CEE(p){22,46}T( khj askjh 1 kh ak hh\n" +
                        "jhkl\n" +
                        "jasdl kjaslkj asjqq9\n" +
                        "k fiuh 23kj hdfkjh assd\n" +
                        "flkjh lkjh fdfasdfkjlh dfsllk\n" +
                        "d8u u hkkj asyu 4lk vl jhksajhd889p3rk sl a, alkj a9\n" +
                        "))sad lkjsalkja aslkla \n" +
                        "&aacute; lasd &amp; aiass da & asdll . asi ua&$\" khj askjh 1 kh ak hhjhkljasdl kjaslkj asjqq9k fiuh 23kj hdfkjh assdflkjh lkjh fdfa\n" +
                        "sdfkjlh dfsllkd8u u \n" +
                        "hkkj asyu 4lk vl jhksajhd889p3rk sl a, alkj a9))sad l\n" +
                        "kjsalkja aslkla &aacute;\n" +
                        " lasd &amp; aiass da & asdll . asi ua&$\" khj askjh 1 kh ak hh\n" +
                        "jhkl\n" +
                        "jasdl kjaslkj asjqq9\n" +
                        "k fiuh 23kj hdfkjh assd\n" +
                        "flkjh lkjh fdfasdfkjlh dfsllk\n" +
                        "d8u u hkkj asyu 4lk vl jhksajhd889p3rk sl a, alkj a9\n" +
                        "))sad lkjsalkja aslkla \n" +
                        "&aacute; lasd &amp; aiass da & asdll . asi ua&$\" khj askjh 1 kh ak hhjhkljasdl kjaslkj asjqq9k fiuh 23kj hdfkjh assdflkjh lkjh fdfa\n" +
                        "sdfkjlh dfsllkd8u u \n" +
                        "hkkj asyu 4lk vl jhksajhd889p3rk sl a, alkj a9))sad l\n" +
                        "kjsalkja aslkla &aacute;\n" +
                        " lasd &amp; aiass da & asdll . asi ua&$\" khj askjh 1 kh ak hh\n" +
                        "jhkl\n" +
                        "jasdl kjaslkj asjqq9\n" +
                        "k fiuh 23kj hdfkjh assd\n" +
                        "flkjh lkjh fdfasdfkjlh dfsllk\n" +
                        "d8u u hkkj asyu 4lk vl jhksajhd889p3rk sl a, alkj a9\n" +
                        "))sad lkjsalkja aslkla \n" +
                        "&aacute; lasd &amp; aiass da & asdll . asi ua&$\" khj askjh 1 kh ak hhjhkljasdl kjaslkj asjqq9k fiuh 23kj hdfkjh assdflkjh lkjh fdfa\n" +
                        "sdfkjlh dfsllkd8u u \n" +
                        "hkkj asyu 4lk vl jhksajhd889p3rk sl a, alkj a9))sad l\n" +
                        "kjsalkja aslkla &aacute;\n" +
                        " lasd &amp; aiass da & asdll . asi ua&$\" khj askjh 1 kh ak hh\n" +
                        "jhkl\n" +
                        "jasdl kjaslkj asjqq9\n" +
                        "k fiuh 23kj hdfkjh assd\n" +
                        "flkjh lkjh fdfasdfkjlh dfsllk\n" +
                        "d8u u hkkj asyu 4lk vl jhksajhd889p3rk sl a, alkj a9\n" +
                        "))sad lkjsalkja aslkla \n" +
                        "&aacute; lasd &amp; aiass da & asdll . asi ua&$\" khj askjh 1 kh ak hhjhkljasdl kjaslkj asjqq9k fiuh 23kj hdfkjh assdflkjh lkjh fdfa\n" +
                        "sdfkjlh dfsllkd8u u \n" +
                        "hkkj asyu 4lk vl jhksajhd889p3rk sl a, alkj a9))sad l\n" +
                        "kjsalkja aslkla &aacute;\n" +
                        " lasd &amp; aiass da & asdll . asi ua&$\" khj askjh 1 kh ak hh\n" +
                        "jhkl\n" +
                        "jasdl kjaslkj asjqq9\n" +
                        "k fiuh 23kj hdfkjh assd\n" +
                        "flkjh lkjh fdfasdfkjlh dfsllk\n" +
                        "d8u u hkkj asyu 4lk vl jhksajhd889p3rk sl a, alkj a9\n" +
                        "))sad lkjsalkja aslkla \n" +
                        "&aacute; lasd &amp; aiass da & asdll . asi ua&$\" khj askjh 1 kh ak hhjhkljasdl kjaslkj asjqq9k fiuh 23kj hdfkjh assdflkjh lkjh fdfa\n" +
                        "sdfkjlh dfsllkd8u u \n" +
                        "hkkj asyu 4lk vl jhksajhd889p3rk sl a, alkj a9))sad l\n" +
                        "kjsalkja aslkla &aacute;\n" +
                        " lasd &amp; aiass da & asdll . asi ua&$\" khj askjh 1 kh ak hh\n" +
                        "jh){22,48}]");
        testDoc(
                "[#div class \n\n= \n'lala'li=\nlla][/div]",
                "[OES(div){1,1}A(class){1,8}( \n\n= \n){1,13}('lala'){4,1}A(li){4,7}(=\n){4,9}(lla){5,1}OEE(div){5,4}CES(div){5,6}CEE(div){5,12}]");

        System.out.println("TOTAL Test executions: " + totalTestExecutions);
        
        
    }
    
    
    
    static void testDocError(final String input, final String outputBreakDown, final int errorLine, final int errorCol) {
        try {
            testDoc(input, outputBreakDown);
            throw new ComparisonFailure(null, "exception", "no exception");
            
        } catch (final TextParseException e) {
            if (errorLine != -1) {
                assertEquals(Integer.valueOf(errorLine), e.getLine());
            } else {
                assertNull(e.getLine());
            }
            if (errorCol != -1) {
                assertEquals(Integer.valueOf(errorCol), e.getCol());
            } else {
                assertNull(e.getCol());
            }
        }
    }

    
    static void testDoc(final String input, final String output) throws TextParseException {
        testDoc(input.toCharArray(), output, 0, input.length());
    }
    
    static void testDoc(String input, final String output, final int offset, final int len) throws TextParseException {
        testDoc(input.toCharArray(), output, offset, len);
    }


    static void testDoc(final String input, final String outputCommentsProcessed, final String outputCommentsUnprocessed) throws TextParseException {
        testDoc(input.toCharArray(), outputCommentsProcessed, outputCommentsUnprocessed, 0, input.length());
    }

    static void testDoc(String input, final String outputCommentsProcessed, final String outputCommentsUnprocessed, final int offset, final int len) throws TextParseException {
        testDoc(input.toCharArray(), outputCommentsProcessed, outputCommentsUnprocessed, offset, len);
    }

    
    
    
    static void testDoc(final char[] input, final String output,
            final int offset, final int len) throws TextParseException {
        testDoc(input, output, output, offset, len);
    }


    static void testDoc(final char[] input, final String outputCommentsProcessed, final String outputCommentsUnprocessed,
                        final int offset, final int len) throws TextParseException {

        final int maxBufferSize = 16384;
        for (int bufferSize = 1; bufferSize <= maxBufferSize; bufferSize++) {
            testDoc(input, outputCommentsProcessed, outputCommentsUnprocessed, offset, len, bufferSize);
        }

    }


    static void testDoc(
            final char[] input,
            final String output,
            final int offset, final int len, final int bufferSize)
            throws TextParseException {
        testDoc(input, output, output, offset, len, bufferSize);
    }


    static void testDoc(
            final char[] input,
            final String outputCommentsProcessed, final String outputCommentsUnprocessed,
            final int offset, final int len, final int bufferSize)
            throws TextParseException {
        testDoc(input, outputCommentsProcessed, offset, len, bufferSize, true);
        testDoc(input, outputCommentsUnprocessed, offset, len, bufferSize, false);
    }



    static void testDoc(
            final char[] input, 
            final String output,
            final int offset, final int len, final int bufferSize,
            final boolean processComments)
            throws TextParseException {

        try {

            final TextParser parser = new TextParser(2, bufferSize, processComments);

            // TEST WITH TRACING HANDLER AND READER
            {

                final TraceBuilderTextHandler traceHandler = new TraceBuilderTextHandler();
                ITextHandler handler = new TextEventProcessorHandler(traceHandler);

                if (offset == 0 && len == input.length) {
                    parser.parseDocument(new CharArrayReader(input), bufferSize, handler);
                } else { 
                    parser.parseDocument(new CharArrayReader(input, offset, len), bufferSize, handler);
                }

                final List<TextTraceEvent> trace = traceHandler.getTrace();
                final StringBuilder strBuilder = new StringBuilder();
                for (final TextTraceEvent event : trace) {
                    if (event.getEventType().equals(TextTraceEvent.EventType.DOCUMENT_START)) {
                        strBuilder.append("[");
                    } else if (event.getEventType().equals(TextTraceEvent.EventType.DOCUMENT_END)) {
                        strBuilder.append("]");
                    } else {
                        strBuilder.append(event);
                    }
                }

                final String result = strBuilder.toString();
                if (output != null) {
                    assertEquals(output, result);
                }
            }

            // TEST WITH TRACING HANDLER AND NO READER WITH PADDING
            {

                final char[] newInput = new char[len + 10];
                newInput[0] = 'X';
                newInput[1] = 'X';
                newInput[2] = 'X';
                newInput[3] = 'X';
                newInput[4] = 'X';
                System.arraycopy(input,offset,newInput,5,len);
                newInput[newInput.length - 1] = 'X';
                newInput[newInput.length - 2] = 'X';
                newInput[newInput.length - 3] = 'X';
                newInput[newInput.length - 4] = 'X';
                newInput[newInput.length - 5] = 'X';

                final TraceBuilderTextHandler traceHandler = new TraceBuilderTextHandler();
                ITextHandler handler = new TextEventProcessorHandler(traceHandler);

                parser.parseDocument(new CharArrayReader(newInput, 5, len), bufferSize, handler);

                final List<TextTraceEvent> trace = traceHandler.getTrace();
                final StringBuilder strBuilder = new StringBuilder();
                for (final TextTraceEvent event : trace) {
                    if (event.getEventType().equals(TextTraceEvent.EventType.DOCUMENT_START)) {
                        strBuilder.append("[");
                    } else if (event.getEventType().equals(TextTraceEvent.EventType.DOCUMENT_END)) {
                        strBuilder.append("]");
                    } else {
                        strBuilder.append(event);
                    }
                }

                final String result = strBuilder.toString();
                if (output != null) {
                    assertEquals(output, result);
                }
            }

            
            totalTestExecutions++;
            
        } catch (final ComparisonFailure cf) {
            System.err.println("Error parsing text \"" + new String(input, offset, len) + "\" with buffer size: " + bufferSize);
            throw cf;
        } catch (final Exception e) {
            throw new TextParseException("Error parsing text \"" + new String(input, offset, len) + "\" with buffer size: " + bufferSize, e);
        }
        
    }

    
}
