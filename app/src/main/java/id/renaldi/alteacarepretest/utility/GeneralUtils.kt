package id.renaldi.alteacarepretest.utility

import android.content.res.Resources
import android.graphics.drawable.Drawable
import android.os.Build
import android.text.Html
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target

inline val Int.dp: Int
    get() = (this * Resources.getSystem().displayMetrics.density).toInt()

fun ImageView.loadImage(
    url: String,
    roundCorner: Int = 0,
    makeItCircle: Boolean = false,
    placeholder: Int? = null,
    skipMemory: Boolean = false,
    onError: (() -> Unit)? = null,
    onLoadDone: (() -> Unit)? = null
) {
    val builder = Glide.with(this)
        .load(url)
        .diskCacheStrategy(DiskCacheStrategy.RESOURCE)

    if (makeItCircle) builder.circleCrop()
    else if (roundCorner > 0) builder.transform(CenterCrop(), RoundedCorners(roundCorner.dp))

    builder.placeholder(
        placeholder ?: android.R.drawable.progress_indeterminate_horizontal
    ).error(android.R.drawable.stat_notify_error).skipMemoryCache(skipMemory)
        .addListener(object : RequestListener<Drawable> {
            override fun onLoadFailed(
                e: GlideException?,
                model: Any?,
                target: Target<Drawable>?,
                isFirstResource: Boolean
            ): Boolean {
                onLoadDone?.invoke()
                onError?.invoke()
                return false
            }

            override fun onResourceReady(
                resource: Drawable?,
                model: Any?,
                target: Target<Drawable>?,
                dataSource: DataSource?,
                isFirstResource: Boolean
            ): Boolean {
                onLoadDone?.invoke()
                return false
            }

        }).into(this)
}

@Suppress("DEPRECATION")
fun TextView.loadHTML(html: String) {
    text = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
        Html.fromHtml(html, Html.FROM_HTML_MODE_COMPACT)
    } else {
        Html.fromHtml(html)
    }
}