package com.example.myapplication;

import android.content.Intent;
import android.graphics.PixelFormat;
import android.media.AudioManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

/**
 * @author yuechou.zhang
 * @since 2021/6/15
 */
public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.button).setOnClickListener(v -> {
            Log.i("zyc", "showFirstView");
            showFirstOverlayView();
        });
        findViewById(R.id.button3).setOnClickListener(v -> {
            Log.i("zyc", "showSecondView");
            showSecondOverlayView();
        });

        //if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        //    AudioManager systemService = getSystemService(AudioManager.class);
        //    systemService.requestAudioFocus(new AudioManager.OnAudioFocusChangeListener() {
        //        @Override
        //        public void onAudioFocusChange(int focusChange) {
        //            Log.i("zyc", "main activity focusChange2:" + focusChange);
        //        }
        //
        //    }, AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN);
        //}
    }

    String logMsg = "　生活本不苦，苦的是欲望过多，身心本无累，累的是背负太多，再苦，都要用今天的微笑，把它吟咏成一段从容的记忆，再累，都要用当下的遗忘穿越万道红尘，让心波澜不惊，认识一个人靠缘分，了解一个人靠耐心，征服一个人靠智慧，处好一个人靠包容。\n" +
        "\n" +
        "生活本来就不完美，用一颗平淡的心看待周围的一切，人生，活得就是一种心情，惟愿天天开心，谁都不是完美的人，都渴望完美，寂寞的时候，鼓励自己，努力才会有所成就，孤独的时刻，时时告诫自己，追求才能有所收获，失意时，不会放弃，得意时，不会骄横，善待生活，守望未来，给自己一个安慰，心安，静好。\n" +
        "\n" +
        "人的一生不长，简单生活不难，有些事，别问别人为什么，多问自己凭什么，凡事都从自己找原因，不为失败怪别人，别做太多自己不想做的事，别只顾及别人的感受，却委屈了自己，抓住属于自己的，从现在开始，不在为任何人活着，只为自己人生理想而做，人不需要多少辉煌，不屈求什么，自己开心，过的随意，\n" +
        "\n" +
        "一个好的心态，做人拿得起，做事放得下，与人处事合得来，人生在世，有得就有失，有付出就有回报，鱼和熊掌不能兼得，有时你的付出不一定能得到回报，但自己要想明白一些，不要太苛求自己，生命总有它的轮回，上帝是公平的，它对每个人都是一样的垂青，人生苦短，好好的潇洒做一回自己，追求自己的路，过好自己生活。\n" +
        "\n" +
        "让自己的心灵有一份纯净的湖泊，累了在上面泛泛舟，洗涤风尘创伤，有个朋友，财富不是一个人一生的朋友，而朋友有时则是你一生的财富，人人都希望有朋友，没有朋友的人是可悲又可怜的，但要想有一个真心的朋友又是很难的，朋友不在多而在精，所谓人生得一知己足以，君子之交淡如水，小人之交酒肉亲，就是这个道理。\n" +
        "\n" +
        "人生活的就是一种心情，穷富也好，得失也好，成败也好，都如云烟，风吹即散，人生，是不断收获与放弃的过程，要得之坦然，失之淡然，成不骄，败不馁，不仰望别人的辉煌，不羡慕别人光芒，把握真实的自己，活出自己的本色，听自己内心的感受，只要心是踏实的，日子就是快乐的，生活就是真实，人生就要随意。\n" +
        "\n" +
        "人生简单，善待别人，理解自己，不要玩心计，不要算计人，其实世界并不复杂，复杂的是人心，是复杂的人心让这个世界变得复杂了，人生没必要自寻烦恼，真诚地对待身边的每一个人，微笑着面对每天的生活，做好自己该做的，欲望少一点，自由多一些，过自己的生活，走自己的路，做简简单单的自己，又何愁不会快乐呢。\n" +
        "\n" +
        "对生活要热忱，对人生要有所追求，得意看淡，失意看开，生命的旅途，有坎坷就会有历练，有浮沉就会有懂得，有阳光就会有希望，有雨落就会有诗意，人间三千事，淡然一笑间，面对人生种种境遇，一笑而过，是一种人生的优雅，活着，说简单其实很简单，笑看得失才会海阔天空，心有透明才会春暖花开。\n" +
        "\n" +
        "生活要珍惜，人生多努力，人生该干的要干，该退的要退，是一种睿智，人生该显的要显，该藏的要藏，是一种境界，可以相信别人，但不可以指望别人，不要拒绝善意，无功不受大禄，无助不受大礼，无胆不挣大钱，常与高人交往，闲与雅人相会，乐与亲人分享，人生务须多富有，开心温饱人自由，不想去赚多大钱，做人还是多善良。\n" +
        "\n" +
        "现实人生，感受生活，时光转换，体会到缘分善变，平淡无语，感受了人情冷暖，有心的人，不管你在与不在，都会惦念，无心的情，无论你好与不好，只是漠然，无论繁华与平淡，都是属于自己的风景，要学会面对，人生，何必负赘太多，想开，看开，放开，如此而已，平淡最美，清欢最真，善待生活多努力，珍惜人生要随意";

    String article2 = "文／蒙蒙王\n" +
        "\n" +
        "　　有一种痛叫做，我本可以，却没能坚持。\n" +
        "\n" +
        "　　雄心勃勃定下的目标，只要一星半点的理由就可以化为泡影。\n" +
        "\n" +
        "　　实际上，恒心也是一种修为，是可以通过对自己的认识和了解去挖掘培养的。\n" +
        "\n" +
        "　　1\n" +
        "\n" +
        "　　你的恒心，与你的意愿有关\n" +
        "\n" +
        "　　很多时候，不能坚持并不是因为我们不能吃苦，只是因为我们做某件事情的意愿不强。\n" +
        "\n" +
        "　　我的体力和耐力都不好，长跑常常是忍着头痛恶心硬撑到最后。\n" +
        "\n" +
        "　　因为这个原因，每次跑步前我都有很大的心理压力。加上那些立下的瘦身目标常常不能三两天见效，所以每一次都是心血来潮地开始，虎头蛇尾地结束。\n" +
        "\n" +
        "　　可最近这一年，我却很积极地把晨跑坚持了下来。\n" +
        "\n" +
        "　　并不是突然间变坚强，而是因为一个特别不起眼的理由：能够一个人呆一会儿。\n" +
        "\n" +
        "　　自从荣升为两个孩子的妈妈后，我经常忙乱到连上厕所都觉得是一种奢侈。\n" +
        "\n" +
        "　　一大早，把没起床的孩子交给家人，换上运动鞋，在空旷的街道上吹吹凉风，吸吸那尚未被污染的空气，戴上耳机，听几首喜欢的歌……\n" +
        "\n" +
        "　　尽管只有短短的半个小时，但这一切都让我足够迷恋。\n" +
        "\n" +
        "　　虽然我仍然会在跑出四五百米之后心跳加快，头疼，手臂和腿都酸困地抬不起来。可对我来说，只要能出去，其他都不是什么大事。\n" +
        "\n" +
        "　　原先看起来无法克服的困难，现在只要稍稍放慢脚步，调整呼吸，不一会儿便能缓解了。\n" +
        "\n" +
        "　　坚持就是痛苦和心理需求博弈的过程。如果痛苦更明显，坚持就会变得艰难；如果心理需求更胜，坚持就只是自我成全的必经之路而已。\n" +
        "\n" +
        "　　心之所向，行之所至。\n" +
        "\n" +
        "　　只要从心底里非常想做某件事，就一定会调动身体里的所有潜能，积极配合。\n" +
        "\n" +
        "　　2\n" +
        "\n" +
        "　　你的恒心，与你的节奏有关\n" +
        "\n" +
        "　　当我们立下FLAG的时候，可能一开始都会忍不住下狠劲儿。\n" +
        "\n" +
        "　　之前一步都不想跑，决定健身了，就2公里开跑，冲刺5公里。\n" +
        "\n" +
        "　　之前一页书也不想看，决定勤奋了，就焚膏油以继晷，好像不熬夜都不好意思说自己看书。\n" +
        "\n" +
        "　　之前从没拉过筋，决定瑜伽了，就恨不得立刻把那支僵硬了几十年的老腿想掰哪儿就掰哪儿。\n" +
        "\n" +
        "　　这种激情维持不了多久，很快就会被打回原形。\n" +
        "\n" +
        "　　坚持从来就不是持续消耗，所以太用力的人一般都跑不远。\n" +
        "\n" +
        "　　作家村上春树的生活里有两件非常重要的事情：写作和跑步。\n" +
        "\n" +
        "　　他全职写作没多久，发现身体变差，便开始跑步。\n" +
        "\n" +
        "　　在普通人眼中，职业小说家应该是不舍昼夜地伏案工作，资深跑者的锻炼强度更难以想象。可村上春树的生活却有张有弛，十分规律。每天早起后的三四个小时集中精力写作，午休后跑步，日暮时读书、听音乐。\n" +
        "\n" +
        "　　在与写作和跑步相伴的几十年里，他既勤勉耐劳、不惜体力，又谨小慎微地呵护自己的热情，既怕惰性来袭，又怕用力过猛。\n" +
        "\n" +
        "　　他说：要让惯性的轮子以一定的速度准确无误地旋转到最后。\n" +
        "\n" +
        "　　坚持，从长远看，要循序渐进，就每一天来说，要量力而为。\n" +
        "\n" +
        "　　既要摸清自己的节奏，也要管住自己按这样的节奏坚持下去。不盲从，也不随性，不蛮干，也不懒散。\n" +
        "\n" +
        "　　只有不乱节奏，才能持之以恒。\n" +
        "\n" +
        "　　3\n" +
        "\n" +
        "　　你的恒心，与你的心态有关\n" +
        "\n" +
        "　　坚持不下去的另一个原因，恐怕是因为我们想太多。\n" +
        "\n" +
        "　　健身两周，就希望身材赛过谁；看了两本书，就期待生活有什么不同；勤奋两个月，就算计着什么时候能够功成名就……\n" +
        "\n" +
        "　　人心都是肉长的，若是在它上面加了太多的砝码，它就会不堪重负。\n" +
        "\n" +
        "　　欲望太多，就不容易看到希望。\n" +
        "\n" +
        "　　村上春树的第一部作品《且听风吟》和第二部作品《1973年的弹子球》问世后，虽然让他有了一定的知名度，但都没有获得日本文学大奖。\n" +
        "\n" +
        "　　对此他十分淡然，觉得能写出让自己满意的作品才更加重要。\n" +
        "\n" +
        "　　他后来在回忆这段经历时说，那时他还在经营餐厅，甚至觉得没得奖也挺好，至少不会没完没了的接待采访和约稿，影响了生意。\n" +
        "\n" +
        "　　听起来像玩笑，但实际上，无论写书，还是跑步，他只是为了迎合自己，达到为自己设定的目标就好。\n" +
        "\n" +
        "　　无欲则刚。\n" +
        "\n" +
        "　　正是这种尽己所能、顺其自然的心态，才让他更长久地投身于自己所热爱的事情上。\n" +
        "\n" +
        "　　自我消耗从来就不是正确的坚持方式，苦痛也不是恒心的代名词。\n" +
        "\n" +
        "　　坚持不下去的时候，不如停下来审视一下自己，将自己内心的能量挖掘出来，找准自己的节奏，让自己心态更平和一些，而不是一味地逼自己死撑。\n" +
        "\n" +
        "　　任何目标与坚持都是生命长河里微不足道的一段，即使某一段坚持并没有当下立见的好结果，但正确的坚持习惯却会让我们越来越有力量，对每一次新的挑战都从容笃定。\n" +
        "\n" +
        "　　只有真正坚持过，你才可以坦然地说一句“尽人事，听天命”。\n" +
        "\n" +
        "　　不留遗憾，不负此生。";

    private void showFirstOverlayView() {
        if (!checkPermission()) {
            return;
        }
        View view = getLayoutInflater().inflate(R.layout.first_view, null);
        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
        layoutParams.gravity = Gravity.CENTER;
        layoutParams.flags = WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL
            | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
        layoutParams.width = WindowManager.LayoutParams.WRAP_CONTENT;
        layoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
        layoutParams.format = PixelFormat.RGBA_8888;
        layoutParams.type = WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY;
        getWindowManager().addView(view, layoutParams);

        printLongLog(1, "zyc", logMsg + article2);
    }


    private void showSecondOverlayView() {
        if (!checkPermission()) {
            return;
        }
        View view = getLayoutInflater().inflate(R.layout.second_view, null);
        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
        layoutParams.gravity = Gravity.CENTER;
        layoutParams.flags = WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL
            | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
        layoutParams.type = WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY;
        layoutParams.width = WindowManager.LayoutParams.WRAP_CONTENT;
        layoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
        layoutParams.format = PixelFormat.RGBA_8888;
        getWindowManager().addView(view, layoutParams);
    }

    public boolean checkPermission() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (!Settings.canDrawOverlays(MainActivity.this)) {
                Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                    Uri.parse("package:" + getPackageName()));
                startActivityForResult(intent, 10);
                return false;
            }
        }
        return true;
    }


    private synchronized static void printLongLog(@NonNull int logLevel, String tag, final String msg) {
        if (msg.length() <= 1000) {
            Log.d(tag, msg);
            return;
        }
        Thread currentThread = Thread.currentThread();
        long currentThreadId = currentThread.getId();
        String currentThreadName = currentThread.getName();

        Thread thread = new Thread(() -> {
            Log.d(tag, "log thread :" + currentThreadId + ", log threadName: " + currentThreadName);
            String logMsg = msg;
            while (logMsg.length() != 0) {
                int end = Math.min(logMsg.length(), 1000);
                Log.d(tag, logMsg.substring(0, end));
                logMsg = logMsg.substring(end);
            }
        });
        thread.start();
    }
}


