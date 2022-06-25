package com.android.onboarding

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.view.size
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager.widget.ViewPager
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.tip_content.view.*
import kotlin.math.abs

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //Remove a statusbar
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN

        setContentView(R.layout.activity_main)

        val tips = arrayOf(
            Tip(
                "Título Um",
                "Descrição Um, Descrição Um, Descrição Um",
                R.drawable.um,
                R.drawable.background_blue
            ),
            Tip(
                "Título Dois",
                "Descrição Dois, Descrição Dois, Descrição Dois",
                R.drawable.dois,
                R.drawable.background_pink
            ),
            Tip(
                "Título Três",
                "Descrição Três, Descrição Três, Descrição Três",
                R.drawable.tres,
                R.drawable.background_green
            ),
            Tip(
                "Título Quatro",
                "Descrição Quatro, Descrição Quatro, Descrição Quatro",
                R.drawable.quatro,
                R.drawable.background_orange
            )
        )

        addDots(tips.size)
        view_pager.adapter = OnboardingAdapter(tips)

        view_pager.setPageTransformer(true) {
            page: View, position: Float ->
            page.alpha = 1 - abs(position)
            page.translationX = -position * page.width
        }

        view_pager.addOnPageChangeListener(object: ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(state: Int) {}

            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {}

            override fun onPageSelected(position: Int) {
                addDots(tips.size, position)
            }
        })

        btn_next.setOnClickListener {
            if (view_pager.currentItem == view_pager.size)
                /*
                Vou colocar um toast provisioriamente, mas pode adicionar um intent
                para chamar uma outra activity
                 */
                Toast.makeText(this, "Abrir tela de Login", Toast.LENGTH_SHORT).show()
            view_pager.setCurrentItem(view_pager.currentItem + 1, true)
        }
    }

    private fun addDots(size: Int, position: Int = 0) {
        dots.removeAllViews()
        Array(size) {
            val textView: TextView = TextView(baseContext).apply {
                text = getText(R.string.dotted)
                textSize = 35f
                setTextColor(
                    if (position == it) ContextCompat.getColor(baseContext, android.R.color.white)
                    else ContextCompat.getColor(baseContext, android.R.color.darker_gray)
                )
            }
            dots.addView(textView)
        }
    }

    private inner class OnboardingAdapter(val tips: Array<Tip>) : PagerAdapter() {

        override fun instantiateItem(container: ViewGroup, position: Int): Any {

            val view: View = layoutInflater.inflate(R.layout.tip_content, container, false)
            with(tips[position]) {
                view.tip_title_um.text = title
                view.tip_description_um.text = description
                view.tip_logo_um.setImageResource(logo)
                view.background = ContextCompat.getDrawable(this@MainActivity, background)
            }
            container.addView(view)
            return view
        }

        override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
            container.removeView(`object` as View)
        }
        override fun isViewFromObject(view: View, `object`: Any): Boolean {
            return view == `object`
        }
        override fun getCount(): Int = tips.size
    }
}