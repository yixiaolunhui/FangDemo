package com.study.fangdemo.zhilian

import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.view.ViewConfiguration
import android.view.ViewGroup.MarginLayoutParams
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetBehavior.*
import com.study.fangdemo.databinding.ActivityMainBinding
import com.study.fangdemo.utils.RecyclerDataUtils


class ZhiLianActivity : AppCompatActivity() {
    private var binding: ActivityMainBinding? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding?.root)
        initView()
        initListener()

    }

    private fun initListener() {
        val behavior = from(binding!!.bottomSheet)
        behavior?.addBottomSheetCallback(object : BottomSheetBehavior.BottomSheetCallback() {
            override fun onStateChanged(bottomSheet: View, newState: Int) {
                //左边图标显示
                binding?.drag?.isVisible = true
                when (newState) {
                    STATE_DRAGGING -> {}
                    STATE_SETTLING -> {}
                    STATE_EXPANDED -> {
                        //展开状态时隐藏左边图标
                        binding?.drag?.isVisible = false
                    }
                    STATE_COLLAPSED -> {}
                    STATE_HIDDEN -> {}
                    STATE_HALF_EXPANDED -> {}
                }
            }

            override fun onSlide(bottomSheet: View, slideOffset: Float) {
                val width = windowManager.defaultDisplay.width
                val margin = ((0.5 - slideOffset / 2) * width).toInt()
                margin(bottomSheet, margin)
            }
        })

        //详情滑动到底部 再往上滑动时展开
        binding?.scrollview?.setOnTouchListener(object : View.OnTouchListener {
            var startX: Float = 0.0f
            var startY: Float = 0.0f
            var offsetX: Float = 0.0f
            var offsetY: Float = 0.0f
            var touchSlop = ViewConfiguration.get(baseContext).scaledTouchSlop
            override fun onTouch(view: View?, event: MotionEvent?): Boolean {
                when (event?.action) {
                    MotionEvent.ACTION_DOWN -> {
                        startX = event.x
                        startY = event.y

                    }
                    MotionEvent.ACTION_UP -> {
                        offsetX = event.x - startX
                        offsetY = event.y - startY
                        if (Math.abs(offsetX) > Math.abs(offsetY)) {
                            // left
                            if (offsetX < -touchSlop) {

                            }
                            // right
                            else if (offsetX > touchSlop) {
                            }
                        } else {
                            // up
                            if (offsetY < -touchSlop) {
                                if (isScrollViewEnd()) {
                                    behavior.state = STATE_EXPANDED
                                }
                            }
                            // down
                            else if (offsetY > touchSlop) {
                            }
                        }
                    }
                }
                return false
            }

        })

    }

    /**
     * NestedScrollView 是否滚动到底部
     */
    private fun isScrollViewEnd(): Boolean {
        binding?.apply {
            var scrollY = scrollview?.scrollY
            var onlyChild = scrollview?.getChildAt(0)
            if (onlyChild?.height!! <= (scrollY!! + scrollview?.height!!)) {
                return true
            }
        }
        return false
    }

    private fun initView() {
        binding?.apply {
            //设置测试列表数据
            RecyclerDataUtils.setRecyclerAdater(baseContext, recyclerview, "测试数据", 30)

            //设置屏幕宽
            bottomSheet?.post {
                val params = bottomSheet.layoutParams
                params.width = windowManager.defaultDisplay.width
                bottomSheet.layoutParams = params
            }

            //默认外左边距是屏幕宽度一半
            margin(bottomSheet, windowManager.defaultDisplay.width / 2)
        }

    }

    /**
     * 设置左边Margin
     */
    private fun margin(v: View, l: Int) {
        if (v.layoutParams is MarginLayoutParams) {
            val p = v.layoutParams as MarginLayoutParams
            p.leftMargin = l
            v.requestLayout()
        }
    }
}