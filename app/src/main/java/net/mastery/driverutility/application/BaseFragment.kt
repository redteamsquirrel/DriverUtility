package net.mastery.driverutility.application

import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding


open class BaseFragment : Fragment() {

  companion object {
    const val USER_ARG_KEY = "user"
  }

  open val baseActivity: BaseActivity?
    get() = activity as? BaseActivity

  protected var _binding: ViewBinding? = null
  // This property is only valid between onCreateView and onDestroyView.
  protected open val binding get() = _binding!!

  override fun onDestroyView() {
    super.onDestroyView()
    _binding = null
  }

  open fun hideFragmentViewKeyboard(view: View) {
    val imm: InputMethodManager = context?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow(view.windowToken, 0)
  }

}